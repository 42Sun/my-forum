package com.sundingyi.forum.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;
    
    CommentTypeEnum(Integer type) {
        this.type = type;
    }
    
    public static boolean exists(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    public Integer getType() {
        return type;
    }
}
