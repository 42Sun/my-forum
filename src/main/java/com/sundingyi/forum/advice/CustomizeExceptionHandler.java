package com.sundingyi.forum.advice;

import com.alibaba.fastjson.JSON;
import com.sundingyi.forum.dto.ResultDTO;
import com.sundingyi.forum.exception.CustomizeErrorCode;
import com.sundingyi.forum.exception.CustomizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleControllerException(HttpServletRequest request,
                                                  Throwable e,
                                                  HttpServletResponse response) throws IOException {
        String contentType = request.getContentType();
        ModelAndView modelAndView = new ModelAndView();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO = null;
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            response.setContentType("application/json");
            response.setStatus(200);
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(resultDTO));
            writer.close();
            return null;
        } else {
            
            if (e instanceof CustomizeException) {
                modelAndView.addObject("message", e.getMessage());
            } else {
                ResultDTO resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }
            modelAndView.setViewName("error");
            return modelAndView;
        }
        
    }
}
