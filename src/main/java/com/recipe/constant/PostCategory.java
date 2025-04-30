package com.recipe.constant;

public enum PostCategory {
    TIP("노하우"), FRIDGE_PRIDE("냉장고 자랑"), DISH_PRIDE("요리 자랑");

    private final String label;

    PostCategory(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }


}
