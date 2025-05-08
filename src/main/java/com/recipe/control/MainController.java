package com.recipe.control;

import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String mainpage(Model model){

        model.addAttribute("userInfo", userService.getAllUsers() );
        // 모델에 데이터를 담아(에드에트리뷰트라는 메서드를 이용해서) - 이름을 정해서
        // 이름은 문자열 타입으로~~
        return "mainpage";
    }
    // 로그인 시
    @GetMapping("/login")
    public String login(Model model){
       return "user/login";
    }
    // 로그인 실패시 - 다시 로그인 페이지 제공
    @GetMapping("/signIn/error")
    public String loginfail(Model model){
        model.addAttribute("loginErrorMsg","다시 로그인 해주세요.");
        return "user/login";
    }

    // 회원가입 시
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("memberSignUpDto", new MemberSignUpDto() );

        return "user/signup";
    }
    @PostMapping
    public String saveUser(@Valid MemberSignUpDto memberSignUpDto, BindingResult bindingResult){

        if( bindingResult.hasErrors()) {
            return "user/signup";
        }

        userService.saveUser(memberSignUpDto);

        return "/";
    }


    // 내 프로필 편집
    @GetMapping("/profile")
    public String profile(Model model){
        return "user/profile";
    }

    // 내 활동
    @GetMapping("/activity")
    public String activity(Model model){
        return "user/activity";
    }

}
