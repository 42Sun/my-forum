package com.sundingyi.forum.mapper;

import com.sundingyi.forum.model.User;
import com.sundingyi.forum.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    long countByExample(UserExample example);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    int deleteByExample(UserExample example);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    int deleteByPrimaryKey(Long id);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    int insert(User record);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    int insertSelective(User record);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    List<User> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    List<User> selectByExample(UserExample example);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    User selectByPrimaryKey(Long id);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    int updateByPrimaryKeySelective(User record);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Tue Jun 08 16:02:01 CST 2021
     */
    int updateByPrimaryKey(User record);
}