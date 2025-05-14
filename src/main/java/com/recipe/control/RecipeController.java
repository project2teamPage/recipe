package com.recipe.control;

import com.recipe.config.CustomUserDetails;
import com.recipe.constant.DishType;
import com.recipe.constant.OrderType;
import com.recipe.constant.Theme;
import com.recipe.dto.recipe.*;
import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.user.User;
import com.recipe.repository.recipe.RecipeRepo;
import com.recipe.repository.user.UserRepo;
import com.recipe.service.FileService;
import com.recipe.service.recipe.RecipeService;
import com.recipe.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final FileService fileService;
    private final RecipeRepo recipeRepo;

    // 레시피 목록
    @GetMapping("/recipe")
    public String filteredRecipe(@RequestParam(required = false)DishType dishType,
                                 @RequestParam(required = false)Theme theme,
                                 @RequestParam(required = false)Integer spicy,
                                 @RequestParam(defaultValue = "RECENT") OrderType orderType,
                                 @PageableDefault(size = 16, sort = "uploadDate", direction = Sort.Direction.DESC) Pageable pageable,
                                 Model model){

        Page<RecipeListDto> recipePage = recipeService.recipeListPage(dishType, theme, spicy, orderType, pageable);
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("totalPages", recipePage.getTotalPages());
        model.addAttribute("currentPage", recipePage.getNumber() + 1);


        return "recipe/list";
    }



    // 레시피 상세
    @GetMapping("/recipe/{id}")
    public String recipeDetail(@PathVariable Long id, Model model) {
        RecipeDetailDto recipeDetailDto = recipeService.recipeDetail(id);
        model.addAttribute("recipe", recipeDetailDto);
        model.addAttribute("newComment", new RecipeCommentDto() );
        return "recipe/detail";
    }

    // 레시피 댓글 작성
    @PostMapping("recipe/{id}/comment")
    public String newComment(@PathVariable Long id, @ModelAttribute("newComment") RecipeCommentDto recipeCommentDto,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();

        recipeService.saveComment(id, user, recipeCommentDto);


        return "redirect:/recipe/" + id;
    }


    // 레시피 삭제

    @DeleteMapping("/recipe/{id}")
    public String recipeDelete(@PathVariable Long id){
        recipeService.deleteRecipe(id);

        return "recipe/list";
    }
    // 레시피 작성

    @GetMapping("/recipe/new")
    public String recipeNew(Model model){
        model.addAttribute("recipeForm", new RecipeForm() );

        return "recipe/recipeForm";
    }

    @PostMapping("/recipe/new")
    public String recipeSave(@Valid RecipeForm recipeForm, BindingResult bindingResult,
                             Model model){

        if(bindingResult.hasErrors()){ // 필수입력값 을 작성하지 않은 경우
            return "recipe/recipeForm";
        }
        List<RecipeStepDto> stepList = recipeForm.getRecipeStepDtoList();

        if( stepList == null || stepList.isEmpty() ){
            // 이미지를 선택하지 않을 경우 에러메세지 보내주기
            model.addAttribute("errorMessage", "each recipeSteps needs image");
            return "recipe/recipeForm";
        }

        try{
            recipeService.createRecipe(recipeForm);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "레시피 작성 실패");
            e.printStackTrace();
            return "recipe/recipeForm";
        }


        return "redirect:/recipe";
    }

    // 레시피 수정
    @GetMapping("/recipe/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        RecipeForm recipeFrom = recipeService.getRecipeForm(id);

        model.addAttribute("recipeForm", recipeFrom);

        return "recipe/editForm";
    }



}
