package com.sundingyi.forum.service;

import com.sundingyi.forum.dto.CommentDTO;
import com.sundingyi.forum.enums.CommentTypeEnum;
import com.sundingyi.forum.enums.NotificationEnum;
import com.sundingyi.forum.enums.NotificationStatusEnum;
import com.sundingyi.forum.exception.CustomizeErrorCode;
import com.sundingyi.forum.exception.CustomizeException;
import com.sundingyi.forum.mapper.*;
import com.sundingyi.forum.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentMapper commentMapper;
    private final QuestionMapper questionMapper;
    private final MyMapper myMapper;
    private final UserMapper userMapper;
    private final NotificationMapper notificationMapper;
    
    public CommentService(CommentMapper commentMapper, QuestionMapper questionMapper, MyMapper myMapper, UserMapper userMapper, NotificationMapper notificationMapper) {
        this.commentMapper = commentMapper;
        this.questionMapper = questionMapper;
        this.myMapper = myMapper;
        this.userMapper = userMapper;
        this.notificationMapper = notificationMapper;
    }
    
    
    @Transactional
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
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            // 若数据库中不存在
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            // 插入评论
            commentMapper.insert(comment);
            // 创建通知
            createNotification(comment, dbComment.getCommentator(), NotificationEnum.REPLY_COMMENT, dbComment.getParentId());
    
        } else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            myMapper.updateQuestionCommentCountById(comment.getParentId());
            // 创建通知
            createNotification(comment, question.getCreator(), NotificationEnum.REPLY_QUESTION, question.getId());
        }
    }
    
    private void createNotification(Comment comment, Long receiver, NotificationEnum notificationEnum, Long questionId) {
        // 通知
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationEnum.getType());
        notification.setOuterid(questionId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notificationMapper.insert(notification);
    }
    
    public List<CommentDTO> listByIdAndType(Long id, CommentTypeEnum typeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(typeEnum.getType());
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
        if (comments.size() == 0) {
            return null;
        }
        Set<Long> commentator = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(new ArrayList<>(commentator));
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
    
    public List<CommentDTO> listByIdListAndType(List<Long> idList, CommentTypeEnum typeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdIn(idList).andTypeEqualTo(typeEnum.getType());
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
        if (comments.size() == 0) {
            return null;
        }
        Set<Long> commentator = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(new ArrayList<>(commentator));
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
