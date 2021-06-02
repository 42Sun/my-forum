package com.sundingyi.forum.controller;

import com.sundingyi.forum.dto.CommentDTO;
import com.sundingyi.forum.dto.ResultDTO;
import com.sundingyi.forum.mapper.CommentMapper;
import com.sundingyi.forum.model.Comment;
import com.sundingyi.forum.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    private final CommentMapper commentMapper;
    
    public CommentController(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
    
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        
        User user = (User) request.getSession().getAttribute("githubUser");
        if (user == null) {
            return ResultDTO.errorOf(2002, "未登录，请登录");
        }
        
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreat(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreat());
        comment.setCommentator(1);
        comment.setLikeCount(0L);
        Map<Object, Object> map = new HashMap<>();
        map.put("message", "成功");
        return map;
    }
}
