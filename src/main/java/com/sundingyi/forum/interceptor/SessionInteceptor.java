package com.sundingyi.forum.interceptor;

import com.sundingyi.forum.mapper.UserMapper;
import com.sundingyi.forum.model.User;
import com.sundingyi.forum.model.UserExample;
import com.sundingyi.forum.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInteceptor implements HandlerInterceptor {
    private final UserMapper userMapper;
    private final NotificationService notificationService;
    
    public SessionInteceptor(UserMapper userMapper, NotificationService notificationService) {
        this.userMapper = userMapper;
        this.notificationService = notificationService;
    }
    
    
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if (httpServletRequest.getCookies() != null) {
            Cookie[] cookies = httpServletRequest.getCookies();
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        httpServletRequest.getSession().setAttribute("githubUser", users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        httpServletRequest.getSession().setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }
}