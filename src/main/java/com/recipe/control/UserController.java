package com.recipe.control;

import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.dto.user.UserProfileDto;
import com.recipe.entity.user.User;
import com.recipe.repository.user.UserRepo;
import com.recipe.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    // 로그인 시
    @GetMapping("/login")
    public String login(Model model){
        return "user/login";
    }


    // 회원가입 시
    @GetMapping("/user/signup")
    public String signup(Model model){
        model.addAttribute("memberSignUpDto", new MemberSignUpDto() );

        return "user/signup";
    }

    @PostMapping("/user/signup")
    public String saveUser(@Valid MemberSignUpDto memberSignUpDto, BindingResult bindingResult, Model model){

        if( bindingResult.hasErrors()) {
            return "user/signup";
        }
        try {
            userService.saveUser(memberSignUpDto, passwordEncoder);
        }catch (IllegalStateException i) { // 예외 발생 시
            model.addAttribute("errorMessage", "회원가입에 실패했습니다.");
            return "user/signup";
        }

        return "redirect:/user/food";
    }

//    @PostMapping("/user/food")
//    public String saveFood(@Valid MemberSignUpDto memberSignUpDto, BindingResult bindingResult, Model model){
//
//        if()
//    }

    // 회원가입 음식 호불호 페이지
    @GetMapping("/user/food")
    public String food(Model model){
        return "user/food";
    }

    // 내 프로필 편집
    @GetMapping("/user/profile")
    public String profile(Model model, Principal principal){
        String loginId = principal.getName();
        User user = userRepo.findByLoginId(loginId);
        model.addAttribute("user", user);
        
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        
        return "user/profile";
    }

    // 내 활동
    @GetMapping("/user/activity")
    public String activity(Model model){
        return "user/activity";
    }

    // 즐겨찾는 레시피
    @GetMapping("/user/bookmark")
    public String bookmark(Model model){
        return "user/bookmark";
    }


    // 프로필 이미지 전달받기
    @PostMapping("/user/profile")
    public String updateProfile(@ModelAttribute UserProfileDto userProfileDto,
                                @AuthenticationPrincipal Principal principal)throws Exception {
        // @AuthenticationPrincipal로 로그인 사용자 식별
        Long userId = userService.getUserIdByPrincipal(principal); // 로그인 사용자 정보
        userService.updateProfile(userId, userProfileDto);

        return "redirect:/user/profile";
    }


}
