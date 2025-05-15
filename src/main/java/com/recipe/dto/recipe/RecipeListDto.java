package com.recipe.dto.recipe;

import com.recipe.constant.DishType;
import com.recipe.constant.Theme;
import com.recipe.entity.recipe.Recipe;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
public class RecipeListDto {

    private Long id;
    private String title;
    private String nickName;
    private String imgUrl;
    private LocalDateTime uploadDate;
    private DishType dishType;
    private Theme theme;
    private int recipeLikes;

    private static ModelMapper modelMapper = new ModelMapper();

    public static RecipeListDto of(Recipe recipe, String imgUrl, int recipeLikes){
        RecipeListDto dto = modelMapper.map(recipe, RecipeListDto.class);
        dto.setNickName(recipe.getUser().getNickName());
        dto.setRecipeLikes(recipeLikes);
        dto.setImgUrl(imgUrl);

        return dto;

    }

    public String getFormattedDate(){
        // LocalDateTime 을 String 으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return uploadDate != null ? uploadDate.format(formatter) : "";
    }


}
