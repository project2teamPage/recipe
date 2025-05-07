package com.recipe.service.post;

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
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
//    private final FileService fileService; // 이미지 저장을 담당할 서비스

    // 게시글 작성
    public Post savePost(PostCreateDto postCreateDto) {
        User user = userRepo.findById(postCreateDto.getUserId()).orElseThrow();

        Post post = postCreateDto.to(user);
        post.setUploadDate(LocalDateTime.now());
        post.setViewCount(0);
        post.setDeleted(false);

        Post savedPost = postRepo.save(post);

        // 이미지 저장
//        if (postCreateDto.getPostImages() != null) {
//            for (int i = 0; i < postCreateDto.getPostImages().size(); i++) {
//                MultipartFile image = postCreateDto.getPostImages().get(i);
//                if (!image.isEmpty()) {
//                    String imgName = fileService.saveFile(image); // 저장된 파일 이름
//                    String url = "/images/post/" + imgName;
//
//                    PostImage postImage = new PostImage();
//                    postImage.setPost(savedPost);
//                    postImage.setImgName(imgName);
//                    postImage.setOriginalName(image.getOriginalFilename());
//                    postImage.setImgUrl(url);
//                    postImage.setThumbnail(i == 0); // 첫 번째 이미지를 썸네일로
//                    postImageRepo.save(postImage);
//                }
//            }
//        }

        return savedPost;
    }

    // 게시글 리스트 (페이징)
    public Page<PostListDto> getPostList(Pageable pageable) {
        Page<Post> posts = postRepo.findByIsDeletedFalseOrderByUploadDateDesc(pageable);
        return posts.map(post -> {
            PostImage thumbnail = postImageRepo.findFirstByPostIdAndIsThumbnailTrue(post.getId());

            int postLikes = postLikeRepo.countByPostId(post.getId());

            PostListDto dto = new PostListDto();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setNickName(post.getUser().getNickName());
            dto.setUploadDate(post.getUploadDate());
            dto.setImageUrl(thumbnail.getImgUrl());
            dto.setPostLikes(postLikes);

            return dto;
        });
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


