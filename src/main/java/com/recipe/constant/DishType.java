package com.recipe.constant;

public enum DishType {
    MAINDISH("메인요리"), SIDEDISH("반찬"), DRINK("음료"), DESSERT("디저트");


    private final String label;

    DishType(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }



}
