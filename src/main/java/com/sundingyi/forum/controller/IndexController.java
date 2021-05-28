package com.sundingyi.forum.controller;

import com.sundingyi.forum.mapper.UserMapper;
import com.sundingyi.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    final UserMapper userMapper;
    
    public IndexController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    
    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() != null) {
            Cookie[] cookies = httpServletRequest.getCookies();
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        httpServletRequest.getSession().setAttribute("githubUser", user);
                    }
                    break;
                }
            }
        }
    
        return "index";
    }
}
