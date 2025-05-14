package com.recipe.control;

import com.recipe.constant.OrderType;
import com.recipe.constant.PostCategory;
import com.recipe.constant.UploadType;
import com.recipe.dto.post.PostForm;
import com.recipe.repository.post.PostRepo;
import com.recipe.service.FileService;
import com.recipe.service.post.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


        return "post/post";
    }

    // 게시글 작성
    @GetMapping("/post/new")
    public String newPost(Model model){
        model.addAttribute("postForm", new PostForm());

        return "post/postForm";
    }

    @PostMapping("/post/new")
    public String createPost(@Valid PostForm postForm, BindingResult bindingResult,
                                Model model) {

        if( bindingResult.hasErrors() ){
            return "/post/postForm";
        }

        try {
            postService.savePost(postForm);
        } catch(IOException e) {
            model.addAttribute("errorMessage", "게시글 작성 실패");
            return "post/postForm";
        }
        return "redirect:/post";
    }

    // 게시글 상세페이지
    @GetMapping("/post/{postId}")
    public String postDetail(Model model, @PathVariable("postId") Long postId){

        model.addAttribute("postForm", postService.getPostDetail(postId));


        return "post/detail";
    }

    // 게시글 삭제
    @GetMapping("/post/delete/{postId}")
    public String deletePost(@PathVariable("postId") Long postId){

        postService.deletePost(postId);

        return "post/post";
    }

    @PostMapping("/summernote/upload")
    @ResponseBody
    public String uploadSummernoteImage(@RequestParam("file") MultipartFile file) throws IOException {
        // 서버에 저장하고 이미지 URL 반환
        String imgName = fileService.uploadFile(file.getOriginalFilename(), file.getBytes(), UploadType.POST);
        return "/postImg/" + imgName; // 브라우저에서 접근 가능한 경로 반환
    }





}
