package com.recipe.control;

import com.recipe.config.CustomUserDetails;
import com.recipe.constant.PostCategory;
import com.recipe.constant.Role;
import com.recipe.dto.admin.NoticeDto;
import com.recipe.dto.admin.NoticeListDto;
import com.recipe.dto.post.PostListDto;
import com.recipe.dto.recipe.RecipeListDto;
import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.entity.user.User;
import com.recipe.service.MainService;
import com.recipe.service.admin.NoticeService;
import com.recipe.service.post.PostService;
import com.recipe.service.recipe.RecipeService;
import com.recipe.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    private final UserService userService;
    private final MainService mainService;
    private final RecipeService recipeService;
    private final PostService postService;
    private final NoticeService noticeService;

    @GetMapping("/")
    public String mainpage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails){

        // 로그인시 메인페이지 닉네임 출력용
        if(userDetails != null){
            User user = userDetails.getUser();

            model.addAttribute("user", user);
            model.addAttribute("isLogin", true);
        } else {
            model.addAttribute("isLogin", false);
        }

        // 모델에 데이터를 담아(에드에트리뷰트라는 메서드를 이용해서) - 이름을 정해서
        // 이름은 문자열 타입으로~~
        model.addAttribute("userInfo", mainService.getAllUsers() );


        // 인기 레시피 목록
        List<RecipeListDto> likedRecipes = recipeService.getLikedRecipes();
        model.addAttribute("likedRecipes", likedRecipes);

        // 요리자랑 랜덤목록
        List<PostListDto> posts = postService.getMainPost(PostCategory.DISH_PRIDE);
        model.addAttribute("posts", posts);

        // 노하우 랜덤목록
        List<PostListDto> posts2 = postService.getMainPost(PostCategory.TIP);
        model.addAttribute("posts2", posts2);






        // 공지사항 최신순
        List<NoticeListDto> noticeListDtos = noticeService.getNoticesByRole(Role.USER);
        List<NoticeListDto> notice5 = noticeListDtos.size() > 5? noticeListDtos.subList(0, 5) : noticeListDtos;

        model.addAttribute("notice5", notice5);



        return "mainpage";
    }

    @GetMapping("/search/{ingredients}")
    @ResponseBody
    public List<RecipeListDto> searchRecipes(@PathVariable String ingredients) {
        List<String> ingredientList = Arrays.asList(ingredients.split(","));
        return recipeService.findByIngredients(ingredientList);
    }


    // 이용약관
    @GetMapping("/user/terms")
    public String terms(Model model){
        return "user/terms";
    }
    // 개인정보처리방침
    @GetMapping("/user/personal")
    public String personal(Model model){
        return "user/personal";
    }

    @GetMapping("/notice")
    public String userNoticeList(Model model) {
        List<NoticeListDto> noticeListDtos = noticeService.getNoticesByRole(Role.USER);
        model.addAttribute("noticeList", noticeListDtos);
        return "user/notice";  // 사용자 공지사항 목록 뷰
    }

    @GetMapping("/noticeDetail")
    public String userNoticeDetail(@PathVariable Long id, Model model) {
        try {
            NoticeDto noticeDto = noticeService.getNoticeForUser(id);
            model.addAttribute("notice", noticeDto);
            return "user/noticeDetail"; // 사용자 공지사항 상세 뷰
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error/noticeNotFound"; // 에러 페이지 (선택)
        }
    }
}
