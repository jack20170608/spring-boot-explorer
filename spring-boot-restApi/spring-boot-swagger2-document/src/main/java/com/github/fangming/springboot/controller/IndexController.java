package com.github.fangming.springboot.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
    @RequestMapping(method= RequestMethod.GET)
    String index(){
        return "<h3>index Page, welcome to online store. click <A href='./product/list'>product list</A> </h3>";
    }
}
