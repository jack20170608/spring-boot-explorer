package com.github.fangming.springboot.jdbc.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index(){
        return "Hello,Welcome to the jdbc explorer sample project.<ul> "
            + "<li><a href='h2-console'>H2 Console Link</a> (url-> jdbc:h2:mem:test; username-> jack; password -> )</li>"
            + "<li><a href='jdbc-meta'>DB metadata</a></li>"
            + "<li><a href='registraton/dirtyRead/2'>Dirty Read Sample</a></li>"
            + "<li><a href='registraton/repeatRead/2'>Repeat Read Sample</a></li>"
            +"</ul>";
    }
}
