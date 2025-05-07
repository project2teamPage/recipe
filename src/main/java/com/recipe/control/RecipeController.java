package com.recipe.control;

import com.recipe.constant.DishType;
import com.recipe.constant.Theme;
import com.recipe.dto.recipe.RecipeCreateDto;
import com.recipe.dto.recipe.RecipeDetailDto;
import com.recipe.dto.recipe.RecipeListDto;
import com.recipe.entity.recipe.Recipe;
import com.recipe.service.FileService;
import com.recipe.service.recipe.RecipeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final FileService fileService;

    // 레시피 첫페이지
    @GetMapping("/recipe")
    public String recipePage(Model model, @PageableDefault(size = 12, sort = "uploadDate", direction = Sort.Direction.DESC) Pageable pageable){

        //레시피 리스트
        Page<RecipeListDto> recipePage = recipeService.recipeListPage(pageable);
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("totalPages", recipePage.getTotalPages());
        model.addAttribute("currentPage", recipePage.getNumber() + 1);

        return "recipe/recipe";
    }

    // 카테고리별 필터링된 레시피 목록
    @GetMapping("/recipe/category")
    public String filteredRecipe(@RequestParam(required = false)DishType dishType,
                                 @RequestParam(required = false)Theme theme,
                                 @RequestParam(required = false)Integer spicy,
                                 Pageable pageable, Model model){

        Page<RecipeListDto> recipePage = recipeService.recipeFilter(dishType, theme, spicy, pageable);
        model.addAttribute("recipes", recipePage.getContent());

        return "recipe/recipe";
    }



    // 레시피 상세
    @GetMapping("/recipe/{id}")
    public String recipeDetail(@PathVariable Long id, Model model) {
        RecipeDetailDto recipeDetailDto = recipeService.recipeDetail(id);
        model.addAttribute("recipe", recipeDetailDto);
        return "recipe/detail";
    }

    // 레시피 작성
    @GetMapping("/recipe/new")
    public String recipeNew(Model model){
        model.addAttribute("recipeCreateDto", new RecipeCreateDto() );

        return "recipe/recipeCreate";
    }

    @PostMapping("/recipe/new")
    public String recipeSave(@Valid RecipeCreateDto recipeCreateDto, BindingResult bindingResult,
                             @RequestParam("recipeImgFile") List<MultipartFile> multipartFileList, Model model){

        if(bindingResult.hasErrors()){ // 필수입력값 을 작성하지 않은 경우
            return "recipe/recipeCreate";
        }

        if( multipartFileList.get(0).isEmpty() && recipeCreateDto.getRecipeStepDtoList() == null ){
            // 이미지를 선택하지 않을 경우 에러메세지 보내주기
            model.addAttribute("errorMessage", "each recipeSteps needs image");
            return "recipe/recipeCreate";
        }

        try{
            Recipe recipe = recipeService.saveRecipe(recipeCreateDto); // 레시피 , 재료 저장
            recipeService.saveRecipeStep(recipeCreateDto, multipartFileList, recipe); // 레시피 step 저장
        } catch (Exception e) {
            model.addAttribute("errorMessage", "레시피 작성 실패");
        }


        return "redirect:/recipe";
    }



}
