create table comment
(
    id           bigint auto_increment,
    parent_id    bigint           not null,
    type         int              not null,
    commentator  BIGINT           not null,
    gmt_creat    bigint           not null,
    gmt_modified bigint           not null,
    like_count   bigint default 0 not null,
    constraint COMMENT_PK
        primary key (id)
);