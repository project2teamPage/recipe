package com.recipe.dto;

import com.recipe.entity.PostImage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class PostImageDto {

    private Long id;
    private String imgName;
    private String originalName;
    private String imgUrl;
    private boolean isThumbnail;

    public static PostImageDto from(PostImage postImage){
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(postImage, PostImageDto.class);
    }





}
