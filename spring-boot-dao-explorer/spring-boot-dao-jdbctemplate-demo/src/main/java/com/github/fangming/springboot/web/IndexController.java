package com.github.fangming.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "Please go to the <a href='student/list'>list all students</a>";
    }
}
