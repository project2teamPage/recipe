package com.recipe.control;

import com.recipe.constant.OrderType;
import com.recipe.constant.PostCategory;
import com.recipe.dto.post.PostForm;
import com.recipe.repository.post.PostRepo;
import com.recipe.service.post.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepo postRepo;

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
    public String createPost(@Valid PostForm postForm, BindingResult bindingResult
                                , List<MultipartFile> files ,Model model) {

        if( bindingResult.hasErrors() ){
            return "/post/postForm";
        }
        
        if( files.isEmpty() ) {
            model.addAttribute("errorMessage", "사진은 최소 하나 첨부 필쑤");
        }else{
            postForm.setPostImages(files);
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




}
