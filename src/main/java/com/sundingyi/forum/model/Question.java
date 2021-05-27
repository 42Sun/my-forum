package com.sundingyi.forum.model;

import lombok.Data;

@Data
public class Question {
    private long id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private long creator;
    private long commentCount;
    private long viewCount;
    private long likeCount;
    private String tag;
}
