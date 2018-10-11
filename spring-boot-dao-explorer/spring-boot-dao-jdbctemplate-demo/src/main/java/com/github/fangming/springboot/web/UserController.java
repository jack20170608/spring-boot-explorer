package com.github.fangming.springboot.web;

import com.github.fangming.springboot.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping(value = "list", produces = "json/")
    public List<User> list(){

    }
}
