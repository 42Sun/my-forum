package com.sundingyi.forum.controller;

import com.sundingyi.forum.mapper.QuestionMapper;
import com.sundingyi.forum.mapper.UserMapper;
import com.sundingyi.forum.model.Question;
import com.sundingyi.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    final QuestionMapper questionMapper;
    final UserMapper userMapper;
    
    public PublishController(QuestionMapper questionMapper, UserMapper userMapper) {
        this.questionMapper = questionMapper;
        this.userMapper = userMapper;
    }
    
    
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
    
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest httpServletRequest,
                            Model model) {
        User user = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null) {
                    httpServletRequest.getSession().setAttribute("githubUser", user);
                }
                break;
            }
        }
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        
        
        questionMapper.create(question);
        
        return "redirect:/";
    }
}
