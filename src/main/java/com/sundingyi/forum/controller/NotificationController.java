package com.sundingyi.forum.controller;

import com.sundingyi.forum.model.User;
import com.sundingyi.forum.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    private final NotificationService notificationService;
    
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    
    @GetMapping("/notification/{id}")
    public String profile(@PathVariable("id") Long id,
                          HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getSession().getAttribute("githubUser");
        if (user == null) {
            return "redirect:/";
        }
        Long outerid = notificationService.read(id, user);
        
        return "redirect:/question/" + outerid;
    }
}
