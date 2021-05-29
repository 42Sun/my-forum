package com.sundingyi.forum.interceptor;

import com.sundingyi.forum.mapper.UserMapper;
import com.sundingyi.forum.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInteceptor implements HandlerInterceptor {
    final
    UserMapper userMapper;
    
    public SessionInteceptor(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if (httpServletRequest.getCookies() != null) {
            Cookie[] cookies = httpServletRequest.getCookies();
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        httpServletRequest.getSession().setAttribute("githubUser", user);
                    }
                    break;
                }
            }
        }
        return true;
    }
}