package com.sundingyi.forum.service;

import com.sundingyi.forum.mapper.UserMapper;
import com.sundingyi.forum.model.User;
import com.sundingyi.forum.model.UserExample;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            User dbUser = users.get(0);
            
            User userUpdate = new User();
            userUpdate.setGmtModified(System.currentTimeMillis());
            userUpdate.setAvatarUrl(user.getAvatarUrl());
            userUpdate.setName(user.getName());
            userUpdate.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(userUpdate, example);
        }
    }
}
