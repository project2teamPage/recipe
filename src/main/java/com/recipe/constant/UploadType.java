package com.recipe.constant;

public enum UploadType {
    RECIPE("레시피"), POST("커뮤니티");

    private final String label;

    UploadType(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

}
