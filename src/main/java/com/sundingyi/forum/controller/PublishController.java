package com.sundingyi.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublishController {
    @RequestMapping("/publish")
    public String publish() {
        return "publish";
    }
}
