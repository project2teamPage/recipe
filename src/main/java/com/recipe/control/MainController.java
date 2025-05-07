package com.recipe.control;

import com.recipe.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String mainpage(Model model){

//        User user = new User();
//        user.setNickName("신선우");
//        user.setPassword("1234");
//
//        User user2 = new User();
//        user2.setNickName("신선우2");
//        user2.setPassword("12345");
//
//        User user3 = new User();
//        user3.setNickName("신선우3");
//        user3.setPassword("123456");
//
//        List<User> userList = new ArrayList<>();
//        userList.add(user);
//        userList.add(user2);
//        userList.add(user3);
//
//        // 객체에 데이터를 넣은 다음에 모델에 넣어 넘김
//       User user = new User();
//       user.setNickName("신선우");
//       user.setPassword("1234");

        model.addAttribute("userInfo", userService.getAllUsers() );
        // 모델에 데이터를 담아(에드에트리뷰트라는 메서드를 이용해서) - 이름을 정해서
        // 이름은 문자열 타입으로~~
        return "mainpage";
    }

    // get post
    // get - 대놓고 줌(빠르게확인)
    // post - 전달할 때 봉투에담아서줌(느리게확인)
}
