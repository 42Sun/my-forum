package com.sundingyi.forum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MyMapper {
    
    @Update("update question set view_count = view_count + 1 where id = #{id}")
    void updateQuestionViewCountById(Long id);
    
    @Update("update comment set comment_count = comment_count + 1 where id = #{id}")
    void updateQuestionCommentCountById(Long id);
}
