package com.sundingyi.forum.controller;

import com.sundingyi.forum.dto.PaginationDTO;
import com.sundingyi.forum.mapper.UserMapper;
import com.sundingyi.forum.model.User;
import com.sundingyi.forum.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    final UserMapper userMapper;
    final QuestionService questionService;
    
    public ProfileController(UserMapper userMapper, QuestionService questionService) {
        this.userMapper = userMapper;
        this.questionService = questionService;
    }
    
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest httpServletRequest,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "10") Integer size) {
        User user = (User) httpServletRequest.getSession().getAttribute("githubUser");
        if (user == null) {
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("sectionName", "我的提问");
        }
        if ("replies".equals(action)) {
            model.addAttribute("sectionName", "最新回复");
        }
        model.addAttribute("section", action);
        
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination", paginationDTO);
        return "profile";
    }
    
    @GetMapping("/profile")
    public String profile(Model model) {
        
        model.addAttribute("sectionName", "测试");
        return "profile";
    }
}
