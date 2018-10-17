package com.github.fangming.springboot.jdbc.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index(){
        return "Hello,Welcome to the jdbc explorer sample project";
    }
}
