package com.sundingyi.forum.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class CustomizeErrorController implements ErrorController {
    
    @Override
    public String getErrorPath() {
        return null;
    }
    
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model) {
        HttpStatus httpStatus = getStatus(request);
        if (httpStatus.is4xxClientError()) {
            model.addAttribute("message", "4xx错误");
        }
        if (httpStatus.is5xxServerError()) {
            model.addAttribute("message", "5xx错误");
        }
        return new ModelAndView("error");
        
    }
    
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.resolve(code);
        return (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
