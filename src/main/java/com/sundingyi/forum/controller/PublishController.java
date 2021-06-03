package com.sundingyi.forum.controller;

import com.sundingyi.forum.dto.QuestionDTO;
import com.sundingyi.forum.model.Question;
import com.sundingyi.forum.model.User;
import com.sundingyi.forum.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    private final QuestionService questionService;
    
    public PublishController(QuestionService questionService) {
        this.questionService = questionService;
    }
    
    
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
    
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam(name = "id", required = false) Long id,
                            HttpServletRequest httpServletRequest,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if ("".equals(title) || "".equals(description) || "".equals(tag)) {
            model.addAttribute("error", "请不要留空！");
            return "publish";
        }
        User user = (User) httpServletRequest.getSession().getAttribute("githubUser");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
    
        return "redirect:/question/" + id;
    }
    
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id,
                       Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", id);
        return "publish";
    }
}
