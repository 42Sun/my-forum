package com.sundingyi.forum.mapper;

import com.sundingyi.forum.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

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
    
    @Select("select * from question where creator = #{id} limit #{size} offset #{offset}")
    List<Question> listById(Long id, Integer offset, Integer size);
    
    @Select("select count(*) from question where creator = #{id}")
    Integer countById(Long id);
    
    @Select("select * from question where id = #{id}")
    Question getById(Long id);
    
    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(Question question);
}
