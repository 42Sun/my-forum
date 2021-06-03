package com.sundingyi.forum.service;

import com.sundingyi.forum.enums.CommentTypeEnum;
import com.sundingyi.forum.exception.CustomizeErrorCode;
import com.sundingyi.forum.exception.CustomizeException;
import com.sundingyi.forum.mapper.CommentMapper;
import com.sundingyi.forum.mapper.MyMapper;
import com.sundingyi.forum.mapper.QuestionMapper;
import com.sundingyi.forum.model.Comment;
import com.sundingyi.forum.model.Question;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentMapper commentMapper;
    private final QuestionMapper questionMapper;
    private final MyMapper myMapper;
    
    public CommentService(CommentMapper commentMapper, QuestionMapper questionMapper, MyMapper myMapper) {
        this.commentMapper = commentMapper;
        this.questionMapper = questionMapper;
        this.myMapper = myMapper;
    }
    
    
    public void insert(Comment comment) {
        // 父级错误
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        
        // type 错误
        if (comment.getType() == null || !CommentTypeEnum.exists(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_MISSED);
        }
        
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getId());
            // 若数据库中不存在
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            
        } else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            myMapper.updateQuestionCommentCountById(comment.getId());
        }
    }
}
