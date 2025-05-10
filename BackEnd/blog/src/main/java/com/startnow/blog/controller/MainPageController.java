package com.startnow.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPageController {

    @GetMapping("/getTestMessage")
    public String testMessage(){
        return "hello";
    }
}
