package com.sundingyi.forum.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "这个问题不在了"),
    TARGET_PARAM_NOT_FOUND(2001, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "你没有登录"),
    SYSTEM_ERROR(2004, "服务错误"),
    TYPE_PARAM_MISSED(2005, "TYPE 不存在"),
    COMMENT_NOT_FOUND(2006, "你找的评论不存在"),
    CONTENT_IS_EMPTY(2007, "内容为空"),
    READ_NOTIFICATION_FAIL(2008, "你的用户不对"),
    NOTIFICATION_NOT_FOUND(2009, "没找到这个通知");
    
    private String message;
    private Integer code;
    
    @Override
    public String getMessage() {
        return message;
    }
    
    @Override
    public Integer getCode() {
        return code;
    }
    
    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
