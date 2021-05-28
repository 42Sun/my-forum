package com.sundingyi.forum.controller;

import com.sundingyi.forum.dto.QuestionDTO;
import com.sundingyi.forum.mapper.UserMapper;
import com.sundingyi.forum.model.User;
import com.sundingyi.forum.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    final UserMapper userMapper;
    final QuestionService questionService;
    
    public IndexController(UserMapper userMapper, QuestionService questionService) {
        this.userMapper = userMapper;
        this.questionService = questionService;
    }
    
    
    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest,
                        Model model) {
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
        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions", questionList);
        return "index";
    }
}
