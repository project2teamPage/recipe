package com.recipe.service.post;

import com.recipe.constant.OrderType;
import com.recipe.constant.PostCategory;
import com.recipe.constant.UploadType;
import com.recipe.dto.post.*;
import com.recipe.entity.post.Post;
import com.recipe.entity.post.PostComment;
import com.recipe.entity.post.PostImage;
import com.recipe.entity.post.PostLike;
import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.user.User;
import com.recipe.repository.post.PostCommentRepo;
import com.recipe.repository.post.PostImageRepo;
import com.recipe.repository.post.PostLikeRepo;
import com.recipe.repository.post.PostRepo;
import com.recipe.repository.user.UserRepo;
import com.recipe.service.FileService;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final PostImageRepo postImageRepo;
    private final PostCommentRepo postCommentRepo;
    private final PostLikeRepo postLikeRepo;
    private final FileService fileService;

    // 게시글 작성
    @Transactional
    public void savePost(PostForm postForm, String loginId) throws IOException {
        User user = userRepo.findByLoginId(loginId);
        Post post;

        if(postForm.getId() == null){ // 새 게시글 작성
            post = postForm.to(user);
        }else{ // 수정
            post = postRepo.findById(postForm.getId()).orElseThrow();

            if (!post.getUser().getLoginId().equals(loginId)) {
                throw new IllegalStateException("작성자만 수정할 수 있습니다.");
            }

            post.setTitle(postForm.getTitle());
            post.setContent(postForm.getContent());
            post.setPostCategory(postForm.getPostCategory());
            post.setUpdateDate(LocalDateTime.now());

        }

        Post savedPost = postRepo.save(post);

        // 이미지 처리: content에서 이미지 URL 추출 후 첫 번째 이미지를 썸네일로 설정
        String content = postForm.getContent();
        Document doc = Jsoup.parse(content);
        Element imgElement = doc.select("img").first(); // 첫 번째 이미지 추출

        if (imgElement != null) {
            String imgUrl = imgElement.attr("src"); // 이미지 URL 추출

            // 이미지 파일 이름 추출
            String imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1); // URL에서 파일명만 추출
            String originalName = imgName; // 원본 파일명도 URL과 같다고 가정, 필요시 원본 파일명 처리 가능

            PostImage postImage = new PostImage();
            postImage.setPost(savedPost);
            postImage.setImgUrl(imgUrl); // 이미지 URL
            postImage.setImgName(imgName); // 이미지 파일 이름
            postImage.setOriginalName(originalName);
            postImage.setThumbnail(true); // 첫 번째 이미지를 썸네일로 설정
            postImageRepo.save(postImage);
        }



    }

    // 커뮤니티 게시글 리스트
    public Page<PostListDto> getPostList(Pageable pageable, PostCategory postCategory, OrderType orderType) {

        Page<Post> posts;

        switch (orderType){
            case LIKE :
                posts = postRepo.findByPostCategoryAndIsDeletedFalseOrderByLikeCountDesc(postCategory, pageable);
                break;
            case VIEW :
                posts= postRepo.findByPostCategoryAndIsDeletedFalseOrderByViewCountDesc(postCategory, pageable);
                break;
            case RECENT:
            default:
                posts= postRepo.findByPostCategoryAndIsDeletedFalseOrderByUploadDateDesc(postCategory, pageable);

        }

        List<PostListDto> postListDtos = new ArrayList<>();

        for(Post post : posts){
            PostImage thumbnail = postImageRepo.findFirstByPostIdAndIsThumbnailTrue(post.getId());
            int postLikes = postLikeRepo.countByPostId(post.getId());

            postListDtos.add( PostListDto.from(post, thumbnail.getImgName(), postLikes) );
        }

        return new PageImpl<>(postListDtos, pageable, posts.getTotalElements());
    }

    // 게시글 상세 조회
    public PostDetailDto getPostDetail(Long id) {
        Post post = postRepo.findById(id).orElseThrow();
        int postLikes = postLikeRepo.countByPostId(id);

        List<PostComment> comments = postCommentRepo.findByPostId(id);
        List<PostImage> images = postImageRepo.findByPostId(id);

        List<PostCommentDto> commentDtos = new ArrayList<>();
        for(PostComment postComment : comments){
            commentDtos.add( PostCommentDto.from(postComment) );
        }


        List<PostImageDto> imageDtos = new ArrayList<>();
        for(PostImage postImage : images){
            imageDtos.add( PostImageDto.from(postImage) );
        }

        return PostDetailDto.from(post, postLikes, commentDtos, imageDtos);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, String loginId) {
        Post post = postRepo.findById(postId).orElseThrow();

        if(!post.getUser().getLoginId().equals(loginId)){
            throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
        }

        post.setDeleted(true);
        post.setDeletedDate(LocalDateTime.now().plusDays(7));
    }

    // 스케쥴러로 deletedDate 가 될 시 게시글 자동 삭제
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void expireRecipe(){
        List<Post> expired = postRepo.findAllByIsDeletedTrueAndDeletedDateBefore(LocalDateTime.now());

        postRepo.deleteAll(expired);
    }

    // 댓글 작성
    public void saveComment(Long id, User user, PostCommentDto dto) {

        Post post = postRepo.findById(id).orElseThrow();
        if(dto.getUploadDate() != null){
            dto.setUpdateDate( LocalDateTime.now() );
        }
        dto.setUploadDate( LocalDateTime.now() );

        postCommentRepo.save( dto.to(post, user) );

    }


    // 조회수 증가
    @Transactional
    public void increaseViewCount(Long postId) {
        postRepo.increaseViewCount(postId);
    }

    // 이미 좋아요 누른 페이지인지 확인용
    public boolean hasLiked(Long postId, User user){
        Post post = postRepo.findById(postId).orElseThrow();

        return postLikeRepo.existsByPostAndUser(post, user);
    }


    // 좋아요 누를시 작동
    @Transactional
    public boolean toggleLike(Long postId, Long userId) {
        Post post = postRepo.findById(postId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();

        boolean existing = postLikeRepo.existsByPostAndUser(post, user);

        if (existing) {
            postLikeRepo.deleteByPostAndUser(post, user);
            return false; // 좋아요 취소됨
        } else {
            PostLike like = new PostLike();
            like.setPost(post);
            like.setUser(user);
            postLikeRepo.save(like);
            return true; // 좋아요 추가됨
        }
    }

    // 게시글 좋아요 수
    public int getLikeCount(Long postId) {
        return postLikeRepo.countByPostId(postId);
    }

    // 메인페이지 요리자랑 랜덤목록
    public List<PostListDto> getMainPost(PostCategory postCategory) {

        PageRequest pageRequest = PageRequest.of(0,4);

        List<Post> postList = postRepo.findMainPost(postCategory, pageRequest);
        List<PostListDto> postListDtos = new ArrayList<>();

        for(Post post : postList){

            PostImage thumbnail = postImageRepo.findFirstByPostIdAndIsThumbnailTrue(post.getId());
            int postLikes = postLikeRepo.countByPostId(post.getId());

            postListDtos.add( PostListDto.from(post, thumbnail.getImgName(), postLikes) );
        }

        return postListDtos;
    }
}


