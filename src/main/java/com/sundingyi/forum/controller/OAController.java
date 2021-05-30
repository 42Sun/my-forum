package com.sundingyi.forum.controller;

import com.sundingyi.forum.dto.AccessTokenDTO;
import com.sundingyi.forum.dto.GithubUser;
import com.sundingyi.forum.model.User;
import com.sundingyi.forum.provider.GithubProvider;
import com.sundingyi.forum.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class OAController {
    private final GithubProvider githubProvider;
    private final UserService userService;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirectUri}")
    private String redirectUri;
    
    public OAController(GithubProvider githubProvider, UserService userService) {
        this.githubProvider = githubProvider;
        this.userService = userService;
    }
    
    
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse httpServletResponse) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if (githubUser != null) { // 登录成功，写入 cookies 和 session
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
    
            httpServletResponse.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else { // 登录失败，重新登录
            return "redirect:/";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("githubUser");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
