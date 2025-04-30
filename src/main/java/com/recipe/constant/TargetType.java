package com.recipe.constant;

public enum TargetType {
    RECIPE_POST("레시피게시글"), RECIPE_COMMENT("레시피댓글"), COMMUNITY_POST("커뮤니티게시글"), COMMUNITY_COMMENT("커뮤니티댓글");

    private final String typeName;

    TargetType(String typeName) {this.typeName = typeName;}
    public String getTypeName() {return this.typeName;}
}
