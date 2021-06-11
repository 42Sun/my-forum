package com.sundingyi.forum.dto;

import com.sundingyi.forum.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private String type;
    private String outerTitle;
    private Long outerid;
}
