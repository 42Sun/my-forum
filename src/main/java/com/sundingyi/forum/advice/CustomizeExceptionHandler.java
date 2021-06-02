package com.sundingyi.forum.advice;

import com.sundingyi.forum.exception.CustomizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleControllerException(HttpServletRequest request, Throwable e) {
        ModelAndView modelAndView = new ModelAndView();
        if (e instanceof CustomizeException) {
            modelAndView.addObject("message", e.getMessage());
        } else {
            modelAndView.addObject("message", "ee");
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
