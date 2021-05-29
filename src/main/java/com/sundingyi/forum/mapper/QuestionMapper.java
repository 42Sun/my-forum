package com.sundingyi.forum.mapper;

import com.sundingyi.forum.dto.QuestionDTO;
import com.sundingyi.forum.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) values(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);
    
    @Select("select * from question limit #{size} offset #{offset}")
    List<Question> list(Integer offset, Integer size);
    
    @Select("select count(*) from question")
    Integer count();
}
