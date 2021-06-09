package com.sundingyi.forum.controller;

import com.sundingyi.forum.dto.CommentDTO;
import com.sundingyi.forum.dto.QuestionDTO;
import com.sundingyi.forum.enums.CommentTypeEnum;
import com.sundingyi.forum.service.CommentService;
import com.sundingyi.forum.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final CommentService commentService;
    
    public QuestionController(QuestionService questionService, CommentService commentService) {
        this.questionService = questionService;
        this.commentService = commentService;
    }
    
    
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model) {
    
        if (questionService.getById(id) == null) {
            return "error";
        }
        // 累加阅读数
        questionService.increaseView(id);
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> comments = commentService.listByIdAndType(id, CommentTypeEnum.QUESTION);
        if (comments != null) {
            List<Long> idList = comments.stream().map(CommentDTO::getId).collect(Collectors.toList());
            List<CommentDTO> subComments = commentService.listByIdListAndType(idList, CommentTypeEnum.COMMENT);
            model.addAttribute("subComments", subComments);
        }
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
    
        return "question";
    }
}
