package com.recipe.constant;

public enum Theme {
    DIET("다이어트"), SLOW_AGING("저속노화"), VEGAN("채식주의"), LOW_SUGAR("저당"), HIGH_PROTEIN("고단백"), LCHF("저탄고지");

    private final String label;

    Theme(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

}
