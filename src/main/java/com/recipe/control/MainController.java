package com.recipe.control;

import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.service.MainService;
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
    @Autowired
    private MainService mainService;

    @GetMapping("/")
    public String mainpage(Model model){

        model.addAttribute("userInfo", mainService.getAllUsers() );
        // 모델에 데이터를 담아(에드에트리뷰트라는 메서드를 이용해서) - 이름을 정해서
        // 이름은 문자열 타입으로~~
        return "mainpage";
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

}
