package com.recipe.control;

import com.recipe.dto.RecipeDetailDto;
import com.recipe.dto.RecipeListDto;
import com.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/recipe")
    public String recipePage(Model model, @PageableDefault(size = 12, sort = "uploadDate", direction = Sort.Direction.DESC) Pageable pageable){

        //레시피 리스트
        Page<RecipeListDto> recipePage = recipeService.recipeListPage(pageable);
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("totalPages", recipePage.getTotalPages());
        model.addAttribute("currentPage", recipePage.getNumber() + 1);

        return "recipe/recipe";
    }

    // 레시피 상세
    @GetMapping("/recipe/{id}")
    public String recipeDetail(@PathVariable Long id, Model model) {
        RecipeDetailDto recipeDetailDto = recipeService.recipeDetail(id);
        model.addAttribute("recipe", recipeDetailDto);
        return "recipe/detail";
    }



}
