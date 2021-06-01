package com.sundingyi.forum.controller;

import com.sundingyi.forum.dto.CommentDTO;
import com.sundingyi.forum.mapper.CommentMapper;
import com.sundingyi.forum.model.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CommentController {
    private final CommentMapper commentMapper;
    
    public CommentController(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
    
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreat(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreat());
        comment.setCommentator(1);
        comment.setLikeCount(0L);
        commentMapper.insert(comment);
        return null;
    }
}
