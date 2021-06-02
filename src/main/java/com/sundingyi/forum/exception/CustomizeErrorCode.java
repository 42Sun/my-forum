package com.sundingyi.forum.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("这个问题不在了");
    private String message;
    
    public String getMessage() {
        return message;
    }
    
    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
