package com.sundingyi.forum.service;

import com.sundingyi.forum.dto.NotificationDTO;
import com.sundingyi.forum.dto.PaginationDTO;
import com.sundingyi.forum.enums.NotificationEnum;
import com.sundingyi.forum.mapper.NotificationMapper;
import com.sundingyi.forum.mapper.QuestionMapper;
import com.sundingyi.forum.mapper.UserMapper;
import com.sundingyi.forum.model.Notification;
import com.sundingyi.forum.model.NotificationExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    
    private final NotificationMapper notificationMapper;
    private final UserMapper userMapper;
    private final QuestionMapper questionMapper;
    
    public NotificationService(NotificationMapper notificationMapper, UserMapper userMapper, QuestionMapper questionMapper) {
        this.notificationMapper = notificationMapper;
        this.userMapper = userMapper;
        this.questionMapper = questionMapper;
    }
    
    
    public PaginationDTO<NotificationDTO> list(Long id, Integer page, Integer size) {
        int offset = size * (page - 1);
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(id);
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        if (notificationList.size() == 0) {
            return paginationDTO;
        }
        
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notificationList) { // TODO 找标题
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setNotifier(userMapper.selectByPrimaryKey(notification.getNotifier()));
            notificationDTO.setType(NotificationEnum.nameOfType(notification.getType()));
            notificationDTO.setOuterTitle(questionMapper.selectByPrimaryKey(notification.getOuterid()).getTitle());
            notificationDTOS.add(notificationDTO);
            
        }
        paginationDTO.setData(notificationDTOS);
        Integer totalCount = (int) notificationMapper.countByExample(example);
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }
}
