package com.sundingyi.forum.dto;

import com.sundingyi.forum.exception.CustomizeErrorCode;
import com.sundingyi.forum.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;
    
    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    
    public static ResultDTO errorOf(CustomizeErrorCode customizeErrorCode) {
        return errorOf(customizeErrorCode.getCode(), customizeErrorCode.getMessage());
    }
    
    public static ResultDTO okOf() {
        return errorOf(200, "请求成功");
    }
    
    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }
}
