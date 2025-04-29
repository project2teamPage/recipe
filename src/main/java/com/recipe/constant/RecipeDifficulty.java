package com.recipe.constant;

public enum RecipeDifficulty {
    TOO_EASY("하"), EASY("중하"), MEDIUM("중"), HARD("중상"), TOO_HARD("상");

    private final String label;

    RecipeDifficulty(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

}
