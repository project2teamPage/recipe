package com.recipe.control;

import com.recipe.config.CustomUserDetails;
import com.recipe.constant.OrderType;
import com.recipe.constant.PostCategory;
import com.recipe.constant.UploadType;
import com.recipe.dto.post.PostCommentDto;
import com.recipe.dto.post.PostForm;
import com.recipe.entity.user.User;
import com.recipe.repository.post.PostRepo;
import com.recipe.service.FileService;
import com.recipe.service.post.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepo postRepo;
    private final FileService fileService;

    // 커뮤니티 리스트
    @GetMapping("/post")
    public String post(@RequestParam(defaultValue = "RECENT") OrderType orderType,
                       @PageableDefault(size=8, sort="uploadDate", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){


                model.addAttribute("fridgeList", postService.getPostList( pageable , PostCategory.FRIDGE_PRIDE , orderType) );
                model.addAttribute("dishList", postService.getPostList( pageable, PostCategory.DISH_PRIDE , orderType) );
                model.addAttribute("tipList", postService.getPostList( pageable, PostCategory.TIP, orderType ));
                model.addAttribute("orderType", orderType);


        return "post/list";
    }

    // 게시글 작성
    @GetMapping("/post/new")
    public String newPost(Model model){
        model.addAttribute("postForm", new PostForm());

        return "post/postForm";
    }

    @PostMapping("/post/new")
    public String createPost(@Valid PostForm postForm, BindingResult bindingResult,
                             Principal principal, Model model) {

        if( bindingResult.hasErrors() ){
            return "/post/postForm";
        }

        try {
            postService.savePost(postForm, principal.getName() );
        } catch(IOException e) {
            model.addAttribute("errorMessage", "게시글 작성 실패");
            return "post/postForm";
        }
        return "redirect:/post";
    }

    // 게시글 작성시 이미지 업로드
    @PostMapping("/post/imageUpload")
    @ResponseBody
    public String uploadPostImage(@RequestParam("file") MultipartFile file) {
        try {
            String imgName = fileService.uploadFile(file.getOriginalFilename(), file.getBytes(), UploadType.POST);
            String imageUrl = "/postImg/" + imgName; // 본문에 들어갈 이미지 URL

            return imageUrl;
        } catch (IOException e) {
            throw new RuntimeException("Summernote 이미지 업로드 실패", e);
        }
    }

    // 게시글 상세페이지
    @GetMapping("/post/{postId}")
    public String postDetail(Model model, @PathVariable("postId") Long postId){

        model.addAttribute("post", postService.getPostDetail(postId));
        model.addAttribute("newComment", new PostCommentDto() );


        return "post/detail";
    }

    // 게시글 댓글 작성
    @PostMapping("post/{id}/comment")
    public String newComment(@PathVariable Long id, @ModelAttribute("newComment") PostCommentDto postCommentDto,
                             @AuthenticationPrincipal CustomUserDetails userDetails){

        User user = userDetails.getUser();

        postService.saveComment(id, user, postCommentDto);

        return "redirect:/post/" + id;
    }

    // 좋아요 누를 시 좋아요 저장
    @PostMapping("/post/{id}/like")
    public String addLike(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails){

        User user = userDetails.getUser();

        postService.addLike(id, user);

        return "redirect:/post/" + id;
    }

    // 게시글 삭제
    @GetMapping("/post/delete/{postId}")
    public String deletePost(@PathVariable("postId") Long postId){

        postService.deletePost(postId);

        return "post/post";
    }









}
