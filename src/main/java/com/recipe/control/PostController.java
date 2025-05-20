package com.recipe.control;

import com.recipe.config.CustomUserDetails;
import com.recipe.constant.OrderType;
import com.recipe.constant.PostCategory;
import com.recipe.constant.UploadType;
import com.recipe.dto.post.PostCommentDto;
import com.recipe.dto.post.PostForm;
import com.recipe.entity.post.Post;
import com.recipe.entity.post.PostLike;
import com.recipe.entity.user.User;
import com.recipe.repository.post.PostLikeRepo;
import com.recipe.repository.post.PostRepo;
import com.recipe.repository.user.UserRepo;
import com.recipe.service.FileService;
import com.recipe.service.post.PostService;
import com.recipe.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepo postRepo;
    private final FileService fileService;
    private final UserRepo userRepo;
    private final UserService userService;
    private final PostLikeRepo postLikeRepo;


    @GetMapping("/post")
    public String post(@RequestParam(defaultValue = "RECENT") OrderType fridgeOrder,
                       @RequestParam(defaultValue = "RECENT") OrderType dishOrder,
                       @RequestParam(defaultValue = "RECENT") OrderType tipOrder,
            //            @PageableDefault(size=8, sort="uploadDate", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(defaultValue = "0", name = "fridgePage") int fridgePage,
                       @RequestParam(defaultValue = "0", name = "dishPage") int dishPage,
                       @RequestParam(defaultValue = "0", name = "tipPage") int tipPage,
                       Model model) {

        Pageable fridgePageable = PageRequest.of(fridgePage, 8, Sort.by("uploadDate").descending());
        Pageable dishPageable = PageRequest.of(dishPage, 8, Sort.by("uploadDate").descending());
        Pageable tipPageable = PageRequest.of(tipPage, 8, Sort.by("uploadDate").descending());

        model.addAttribute("fridgeList", postService.getPostList(fridgePageable, PostCategory.FRIDGE_PRIDE, fridgeOrder));
        model.addAttribute("dishList", postService.getPostList(dishPageable, PostCategory.DISH_PRIDE, dishOrder));
        model.addAttribute("tipList", postService.getPostList(tipPageable, PostCategory.TIP, tipOrder));

        model.addAttribute("fridgeOrder", fridgeOrder);
        model.addAttribute("dishOrder", dishOrder);
        model.addAttribute("tipOrder", tipOrder);

        model.addAttribute("fridgePage", fridgePage);
        model.addAttribute("dishPage", dishPage);
        model.addAttribute("tipPage", tipPage);

        return "post/list";
    }


    // 게시글 작성
    @GetMapping("/post/new")
    public String newPost(Model model){
        model.addAttribute("postForm", new PostForm());

        return "post/postForm";
    }

    @PostMapping("/post/new")
    public String savePost(@Valid PostForm postForm, BindingResult bindingResult,
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
    public String postDetail(Model model, @PathVariable Long postId,
                             @AuthenticationPrincipal CustomUserDetails userDetails){

        model.addAttribute("post", postService.getPostDetail(postId));
        model.addAttribute("newComment", new PostCommentDto() );

        boolean liked = false;

        if (userDetails != null) {
            User user = userDetails.getUser();
            liked = postService.hasLiked(postId, user);
            model.addAttribute("user", user);

        }

        // 좋아요 누를 시
        model.addAttribute("liked", liked);
        
        // 글 방문 시 조회수 증가
        postService.increaseViewCount(postId);


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

    @PostMapping("/post/{id}/like")
    @ResponseBody
    public ResponseEntity<?> likePost(@PathVariable Long id, Principal principal) {

        String loginId = principal.getName();
        User user = userRepo.findByLoginId(loginId);
        Post post = postRepo.findById(id).orElseThrow();

        // 중복 체크
        if (postLikeRepo.existsByPostAndUser(post, user)) {
            return ResponseEntity.badRequest().body("이미 좋아요를 눌렀습니다.");
        }

        PostLike like = new PostLike();
        like.setPost(post);
        like.setUser(user);
        postLikeRepo.save(like);

        long likeCount = postLikeRepo.countByPost(post);
        return ResponseEntity.ok(likeCount);
    }


    // 게시글 삭제 (임시)
    @PostMapping("/post/delete/{postId}")
    public String deletePost(@PathVariable Long postId, Principal principal){

        postService.deletePost(postId, principal.getName());

        return "redirect:/post";
    }

    // 게시글 수정 페이지
    @GetMapping("post/edit/{id}")
    public String editPost(@PathVariable Long id, Model model){

        Post post = postRepo.findById(id).orElseThrow();
        PostForm postForm = PostForm.from(post);

        model.addAttribute("postForm", postForm);


        return "post/editForm";
    }

    // 게시글 수정 후 저장
    @PostMapping("post/edit/{id}")
    public String editSave(@PathVariable Long id, @Valid PostForm postForm,
                           BindingResult bindingResult, Model model,
                           Principal principal){

        if(bindingResult.hasErrors()){ // 필수입력값 을 작성하지 않은 경우
            return "post/postForm";
        }

        try {
            postService.savePost(postForm, principal.getName() );
        } catch(IOException e) {
            model.addAttribute("errorMessage", "게시글 작성 실패");
            return "post/editForm";
        }

        return "redirect:/post/"+id;
    }

    //좋아요 누를 시
    @ResponseBody
    @PostMapping("/post/like")
    public ResponseEntity<Map<String, Object>> toggleLike(@RequestParam Long postId ,@AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getId(); // 현재 로그인 사용자 ID

        boolean liked = postService.toggleLike(postId, userId);
        int likeCount = postService.getLikeCount(postId);

        Map<String, Object> response = new HashMap<>();
        response.put("liked", liked);
        response.put("likeCount", likeCount);


        return ResponseEntity.ok(response);
    }









}
