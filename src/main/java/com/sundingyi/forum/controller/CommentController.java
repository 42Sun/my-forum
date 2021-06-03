package com.sundingyi.forum.controller;

import com.sundingyi.forum.dto.CommentDTO;
import com.sundingyi.forum.dto.ResultDTO;
import com.sundingyi.forum.exception.CustomizeErrorCode;
import com.sundingyi.forum.mapper.CommentMapper;
import com.sundingyi.forum.model.Comment;
import com.sundingyi.forum.model.User;
import com.sundingyi.forum.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    
    public CommentController(CommentMapper commentMapper, CommentService commentService) {
        this.commentMapper = commentMapper;
        this.commentService = commentService;
    }
    
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        
        User user = (User) request.getSession().getAttribute("githubUser");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
    
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreat(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreat());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
    
        return ResultDTO.okOf();
    }
}
