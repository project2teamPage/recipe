package com.recipe.service.recipe;

import com.recipe.constant.DishType;
import com.recipe.constant.OrderType;
import com.recipe.constant.Theme;
import com.recipe.constant.UploadType;
import com.recipe.dto.recipe.*;
import com.recipe.entity.post.PostLike;
import com.recipe.entity.recipe.*;
import com.recipe.entity.user.User;
import com.recipe.repository.recipe.*;
import com.recipe.repository.user.UserRepo;
import com.recipe.service.FileService;
import com.recipe.service.user.UserService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepo recipeRepo;
    private final UserService userService;
    private final RecipeStepRepo recipeStepRepo;
    private final RecipeLikeRepo recipeLikeRepo;
    private final RecipeIngredientRepo recipeIngredientRepo;
    private final RecipeCommentRepo recipeCommentRepo;
    private final FileService fileService;

    
    // 레시피 작성시 레시피 entity 로 저장
    public Recipe saveRecipe(RecipeForm dto){
        User user = userService.getCurrentUser();
        Recipe recipe;

        if(dto.getId() == null){ // 새 레시피 작성
            recipe = dto.toRecipe(user);
        } else{ // 레시피 수정
            recipe = recipeRepo.findById(dto.getId()).orElseThrow();

            if (!recipe.getUser().getLoginId().equals(user.getLoginId())) {
                throw new IllegalStateException("작성자만 수정할 수 있습니다.");
            }


            recipe.setTitle(dto.getTitle());
            recipe.setDishType(dto.getDishType());
            recipe.setTheme(dto.getTheme());
            recipe.setSpicy(dto.getSpicy());
            recipe.setRecipeDifficulty(dto.getRecipeDifficulty());
            recipe.setRecipeTime(dto.getRecipeTime());

        }

        return recipeRepo.save( recipe );
    }

    // 레시피 재료 저장
    public void saveIngredient(RecipeForm dto, Recipe recipe){

        recipeIngredientRepo.deleteByRecipe(recipe); // 수정 할 경우 대비


        List<RecipeIngredient> recipeIngredientList = dto.toIngredient(recipe);

        for (int i = recipeIngredientList.size() -1; i >=0; i--){
            RecipeIngredient recipeIngredient = recipeIngredientList.get(i);
            if(recipeIngredient.getName() == null || recipeIngredient.getAmount() == null){
                recipeIngredientList.remove(i);
            }

        }

        recipeIngredientRepo.saveAll(recipeIngredientList);


    }

    // 레시피 step 저장
    public void saveRecipeStep(RecipeForm dto, Recipe recipe) throws IOException {

        recipeStepRepo.deleteByRecipe(recipe); // 수정 할 경우 대비


        List<RecipeStepDto> stepDtos = dto.getRecipeStepDtoList();

        if (stepDtos == null || stepDtos.isEmpty()) {
            throw new IllegalArgumentException("조리 단계를 1개 이상 입력해야 합니다.");
        }

        List<RecipeStep> recipeStep = new ArrayList<>();

        for( int i = 0; i < stepDtos.size(); i++ ){
            RecipeStepDto stepDto = stepDtos.get(i);
            MultipartFile file = stepDto.getImgFile();

            String imgName = stepDto.getImgName();
            String originalFileName = stepDto.getImgOriginalName();
            String imgUrl = stepDto.getImgUrl();

            if (!file.isEmpty()) {
                // 새 파일 업로드
                try {
                    originalFileName = file.getOriginalFilename();
                    imgName = fileService.uploadFile(originalFileName, file.getBytes(), UploadType.RECIPE);
                    imgUrl = "/recipeImg/" + imgName;
                } catch (IOException e) {
                    throw new FileUploadException("파일 업로드 중 오류 발생: " + e.getMessage());
                }
            }

            stepDto.setStepOrder(i + 1);
            stepDto.setImgOriginalName(originalFileName);
            stepDto.setImgName(imgName);
            stepDto.setImgUrl(imgUrl);

            recipeStep.add(stepDto.to(recipe));

        }

        // 마지막 이미지 == 썸네일
        for(int i = recipeStep.size()-1; i >= 0 ; i--){
            String imgUrl = recipeStep.get(i).getImgUrl();
            if( imgUrl != null && !imgUrl.trim().isEmpty()){
                RecipeStep thumbnailStep = recipeStep.get(i);
                thumbnailStep.setThumbnail(true);
                break;
            };

        }


        recipeStepRepo.saveAll(recipeStep);

    }

    // 한번에 저장하는 메서드
    @Transactional
    public void saveRecipeAll(RecipeForm dto) throws IOException {

            Recipe recipe = saveRecipe(dto);
            saveIngredient(dto, recipe);
            saveRecipeStep(dto, recipe);
    }

    // 레시피 목록 (카테고리별 포함)
    public Page<RecipeListDto> recipeListPage(DishType dishType, Theme theme, Integer spicy, OrderType orderType, Pageable pageable){

        Page<Recipe> recipes;

        switch (orderType){
            case LIKE :
                recipes = recipeRepo.findByLikes(dishType, theme, spicy, pageable);
                break;
            case VIEW :
                recipes = recipeRepo.findByViews(dishType, theme, spicy, pageable);
                break;
            case RECENT:
            default:
                recipes = recipeRepo.findRecent(dishType, theme, spicy, pageable);

        }

        List<RecipeListDto> recipeListDtoList = new ArrayList<>();

        for(Recipe recipe : recipes.getContent()){
            RecipeStep recipeStep = recipeStepRepo.findByRecipeIdAndIsThumbnailIsTrue(recipe.getId()); // 레시피의 썸네일 step 찾기
            String imgUrl = "";
            if( recipeStep != null && recipeStep.getImgUrl() != null){
                imgUrl = recipeStep.getImgUrl();
            }

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

    // 레시피 댓글 작성
    public void saveComment(Long recipeId, User user, RecipeCommentDto dto){

        Recipe recipe = recipeRepo.findById(recipeId).orElseThrow();
        if(dto.getUploadDate() != null){
            dto.setUpdateDate( LocalDateTime.now() );
        }
        dto.setUploadDate( LocalDateTime.now() );
        recipeCommentRepo.save( dto.to(user, recipe) );

    }


    // 레시피 삭제 ( isDeleted = true, deletedDate + 7일 )
    @Transactional
    public void deleteRecipe(Long recipeId, String loginId){
        Recipe recipe = recipeRepo.findById(recipeId).orElseThrow();

        // 현재 로그인한 사용자 확인
        if (!recipe.getUser().getLoginId().equals(loginId)) {
            throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
        }

        recipe.setDeleted(true);
        recipe.setDeletedDate(LocalDateTime.now().plusDays(7));
    }

    // 스케쥴러로 deletedDate 가 될 시 레시피 자동 삭제
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void expireRecipe(){
        List<Recipe> expired = recipeRepo.findAllByIsDeletedTrueAndDeletedDateBefore(LocalDateTime.now());

        recipeRepo.deleteAll(expired);
    }


    // 레시피 수정하기 위한 recipeFrom 반환
    public RecipeForm getRecipeForm(Long id) {
        Recipe recipe = recipeRepo.findById(id).orElseThrow();
        List<RecipeIngredient> ingredients = recipeIngredientRepo.findAllByRecipeId(id);
        List<RecipeStep> steps = recipeStepRepo.findAllByRecipeIdOrderByStepOrder(id);

        // Entity -> Dto
        List<RecipeIngredientDto> recipeIngredientDtoList = new ArrayList<>();
        for(RecipeIngredient recipeIngredient : ingredients){

            recipeIngredientDtoList.add( RecipeIngredientDto.from(recipeIngredient) );
        }

        // Entity -> Dto
        List<RecipeStepDto> recipeStepDtoList = new ArrayList<>();
        for(RecipeStep recipeStep : steps){

            recipeStepDtoList.add( RecipeStepDto.from(recipeStep) );
        }

        return RecipeForm.from(recipe, recipeIngredientDtoList, recipeStepDtoList);

    }

    @Transactional
    public void increaseViewCount(Long id) {
        recipeRepo.increaseViewCount(id);
    }

    // 이미 좋아요 눌럿는지 확인용
    public boolean hasLiked(Long id, User user) {
        Recipe recipe = recipeRepo.findById(id).orElseThrow();

        return recipeLikeRepo.existsByRecipeAndUser(recipe, user);
    }


    // 좋아요 누를 시
    @Transactional
    public boolean toggleLike(Long recipeId, User user) {
        Recipe recipe = recipeRepo.findById(recipeId).orElseThrow();

        boolean existing = recipeLikeRepo.existsByRecipeAndUser(recipe, user);

        if (existing) {
            recipeLikeRepo.deleteByRecipeAndUser(recipe, user);
            return false; // 좋아요 취소됨
        } else {
            RecipeLike like = new RecipeLike();
            like.setRecipe(recipe);
            like.setUser(user);
            recipeLikeRepo.save(like);
            return true; // 좋아요 추가됨
        }
    }

    public int getLikeCount(Long recipeId) {
        return recipeLikeRepo.countByRecipeId(recipeId);
    }

    // 메인페이지용 레시피 좋아요순으로 3개 가져오기
    public List<RecipeListDto> getLikedRecipes() {

        // 리스트 3개까지 가져온다
        PageRequest pageRequest = PageRequest.of(0, 3);

        List<Recipe> recipeList = recipeRepo.findTop3OrderByLikes(pageRequest);
        List<RecipeListDto> dtoList = new ArrayList<>();

        for(Recipe recipe : recipeList){
            RecipeStep recipeStep = recipeStepRepo.findByRecipeIdAndIsThumbnailIsTrue(recipe.getId()); // 레시피의 썸네일 step 찾기
            String imgUrl = "";
            if( recipeStep != null && recipeStep.getImgUrl() != null){
                imgUrl = recipeStep.getImgUrl();
            }

            int recipeLikes = recipeLikeRepo.countByRecipeId( recipe.getId() );

            dtoList.add( RecipeListDto.of(recipe, imgUrl, recipeLikes) );
        }

        return dtoList;


    }

    // 재료 검색 시 보여줄 레시피리스트
    public List<RecipeListDto> findByIngredients(List<String> ingredientList) {
        List<Recipe> recipes = recipeIngredientRepo.findByIngredientNames(ingredientList, ingredientList.size());
        List<RecipeListDto> dtoList = new ArrayList<>();

        for(Recipe recipe : recipes){
            RecipeStep recipeStep = recipeStepRepo.findByRecipeIdAndIsThumbnailIsTrue(recipe.getId()); // 레시피의 썸네일 step 찾기
            String imgUrl = "";
            if( recipeStep != null && recipeStep.getImgUrl() != null){
                imgUrl = recipeStep.getImgUrl();
            }

            int recipeLikes = recipeLikeRepo.countByRecipeId( recipe.getId() );

            dtoList.add( RecipeListDto.of(recipe, imgUrl, recipeLikes) );
        }

        return dtoList;
    }
}
