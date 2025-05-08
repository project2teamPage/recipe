package com.recipe.control;

import com.recipe.repository.post.PostRepo;
import com.recipe.service.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepo postRepo;



}
