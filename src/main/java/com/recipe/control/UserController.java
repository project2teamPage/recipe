package com.recipe.control;

import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        return "redirect:/login";
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
