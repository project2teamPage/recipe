package com.recipe.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainpage(){

        return "mainpage";
    }

    // get post
    // get - 대놓고 줌(빠르게확인)
    // post - 전달할 때 봉투에담아서줌(느리게확인)
}
