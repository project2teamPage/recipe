package com.recipe.service;

import com.recipe.dto.*;
import com.recipe.entity.*;
import com.recipe.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepo recipeRepo;
    private final UserRepo userRepo;
    private final RecipeStepRepo recipeStepRepo;
    private final RecipeLikeRepo recipeLikeRepo;
    private final RecipeIngredientRepo recipeIngredientRepo;
    private final RecipeCommentRepo recipeCommentRepo;

    
    // 레시피 작성시 entity 로 저장
    public Recipe saveRecipe(RecipeCreateDto recipeCreateDto){

        User user = userRepo.findById( recipeCreateDto.getUserId() ).orElseThrow(); // dto 에 유저Id를 담아 User 객체를 만든 후
        Recipe recipe = recipeCreateDto.toRecipe(user);                             // Recipe 객체에 User 객체를 넣어준다.

        return recipeRepo.save( recipe );
    }

    // 레시피 리스트 페이징
    public Page<RecipeListDto> recipeListPage(Pageable pageable){
        Page<Recipe> recipes = recipeRepo.findAllOrderByUploadDateDesc(pageable);

        List<RecipeListDto> recipeListDtoList = new ArrayList<>();

        for(Recipe recipe : recipes.getContent()){
            RecipeStep recipeStep = recipeStepRepo.findByRecipeIdAndIsThumbnailIsTrue(recipe.getId()); // 레시피의 썸네일 step 찾기
            String imgUrl = recipeStep.getImgUrl(); // 레시피 썸네일 imgUrl

            int recipeLikes = recipeLikeRepo.countByRecipeId( recipe.getId() );

            recipeListDtoList.add( RecipeListDto.of(recipe, imgUrl, recipeLikes) );

        }

        return new PageImpl<>(recipeListDtoList, pageable, recipes.getTotalElements() );

    }

    // 레시피 상세페이지
    public RecipeDetailDto recipeDetail(Long recipeId){

        Recipe recipe = recipeRepo.findByIdAndIsDeletedFalse(recipeId); // recipeId 에 해당되는 레시피 entity
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepo.findAllByRecipeId(recipeId); // 레시피에 해당되는 재료 목록 
        List<RecipeComment> recipeCommentList = recipeCommentRepo.findAllByRecipeIdOrderByUploadDateDesc(recipeId); // 레시피 댓글 목록 
        List<RecipeStep> recipeStepList = recipeStepRepo.findAllByRecipeIdOrderByStepOrder(recipeId); // 레시피 Step 목록
        int recipeLikes = recipeLikeRepo.countByRecipeId(recipeId); // 레시피 좋아요 수

        // Entity -> Dto
        List<RecipeIngredientDto> recipeIngredientDtoList = new ArrayList<>();
        for(RecipeIngredient recipeIngredient : recipeIngredientList){

            recipeIngredientDtoList.add( RecipeIngredientDto.from(recipeIngredient) );
        }

        // Entity -> Dto
        List<RecipeCommentDto> recipeCommentDtoList = new ArrayList<>();
        for(RecipeComment recipeComment : recipeCommentList){

            recipeCommentDtoList.add( RecipeCommentDto.from(recipeComment) );
        }

        // Entity -> Dto
        List<RecipeStepDto> recipeStepDtoList = new ArrayList<>();
        for(RecipeStep recipeStep : recipeStepList){

            recipeStepDtoList.add( RecipeStepDto.from(recipeStep) );
        }

        // Entity -> Dto 한 후 바로 리턴
        return RecipeDetailDto.of(recipe, recipeIngredientDtoList, recipeCommentDtoList, recipeStepDtoList, recipeLikes);

    }

    


}
