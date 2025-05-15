package com.recipe.control;

import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.entity.user.User;
import com.recipe.repository.user.UserRepo;
import com.recipe.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        return "redirect:/";
    }

    // 아이디 중복체크
    @GetMapping("/user-loginId/{loginId}/exists")
    @ResponseBody
    public boolean checkLoginIdDuplication(@PathVariable String loginId) {
        return userService.checkLoginIdDuplication(loginId);
    }
    // 이메일 중복체크
    @GetMapping("/user-email/{email}/exists")
    @ResponseBody
    public boolean checkEmailDuplication(@PathVariable String email) {
        return userService.checkEmailDuplication(email);
    }



//    @PostMapping("/user/food")
//    public String saveFood(@Valid MemberSignUpDto memberSignUpDto, BindingResult bindingResult, Model model){
//
//        if()
//    }

    // 회원가입 음식 호불호 페이지
    public String food(Model model){
        return "user/food";
    }

    // 내 프로필사진 편집
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

    // 회원 탈퇴
    @PostMapping("/user/profile/logout")
    // authentication = 사용자의 신원을 확인하기 위해 씀
    public String getOutUser(@RequestParam String password, Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();

        // 이제 getEmail() 가능!
        String email = user.getEmail();

        boolean result = userService.getOut(email, password);

        if (result) {
            return "redirect:/logout";
        } else {
            model.addAttribute("wrongPassword", "비밀번호가 맞지 않습니다.");
            return "user/profile";
        }


    }

    // 내 활동
    @GetMapping("/user/activity")
    public String activity(Model model, Principal principal){
        String loginId = principal.getName();
        User user = userRepo.findByLoginId(loginId);
        model.addAttribute("user", user);
        return "user/activity";
    }

    // 즐겨찾는 레시피
    @GetMapping("/user/bookmark")
    public String bookmark(Model model, Principal principal){
        String loginId = principal.getName();
        User user = userRepo.findByLoginId(loginId);
        model.addAttribute("user", user);
        return "user/bookmark";
    }


    // 프로필 이미지 전달받기
    @PostMapping("/user/profile")
    public String updateProfile(@RequestParam("profileImage") MultipartFile profileImage,
                                Principal principal) throws Exception {

        Long userId = userService.getUserIdByPrincipal(principal); // 로그인 사용자 ID
        userService.updateProfile(userId, profileImage); // 이미지 업데이트

        return "redirect:/user/profile";
    }





    // 닉네임 클릭 시 유저 캘린더로 이동
//    @GetMapping("/user/{userId}")
//    public String getNickName(@PathVariable Long userId, Model model){
//        User user = userRepo.findByUserId(String.valueOf(userId));
//        if (user != null){
//            model.addAttribute("nickName", user.getNickName());
//            return "/user/calendar"; // 사용자 캘린더 화면 이동
//        }else {
//            return "redirect:/"; // 사용자 없을 경우 홈으로 이동
//        }
//    }
}
