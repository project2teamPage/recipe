package com.recipe.service.post;

import com.recipe.constant.OrderType;
import com.recipe.constant.PostCategory;
import com.recipe.constant.UploadType;
import com.recipe.dto.post.*;
import com.recipe.entity.post.Post;
import com.recipe.entity.post.PostComment;
import com.recipe.entity.post.PostImage;
import com.recipe.entity.user.User;
import com.recipe.repository.post.PostCommentRepo;
import com.recipe.repository.post.PostImageRepo;
import com.recipe.repository.post.PostLikeRepo;
import com.recipe.repository.post.PostRepo;
import com.recipe.repository.user.UserRepo;
import com.recipe.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public Post savePost(PostForm postForm) throws IOException {
        User user = userRepo.findById(postForm.getUserId()).orElseThrow();

        Post post = postForm.to(user);
        post.setUploadDate(LocalDateTime.now());
        post.setViewCount(0);
        post.setDeleted(false);

        Post savedPost = postRepo.save(post);

        // 이미지 저장
        if (postForm.getPostImages() != null) {
            for (int i = 0; i < postForm.getPostImages().size(); i++) {
                MultipartFile image = postForm.getPostImages().get(i);
                if (!image.isEmpty()) {
                    String imgName = fileService.uploadFile(image.getOriginalFilename(), image.getBytes(), UploadType.POST); // 저장된 파일 이름
                    String url = "/postImg/" + imgName;

                    PostImage postImage = new PostImage();
                    postImage.setPost(savedPost);
                    postImage.setImgName(imgName);
                    postImage.setOriginalName(image.getOriginalFilename());
                    postImage.setImgUrl(url);
                    postImage.setThumbnail(i == 0); // 첫 번째 이미지를 썸네일로
                    postImageRepo.save(postImage);
                }
            }
        }

        return savedPost;
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
    public void deletePost(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow();
        post.setDeleted(true);
        post.setDeletedTime(LocalDateTime.now());
        postRepo.save(post);
    }
}


