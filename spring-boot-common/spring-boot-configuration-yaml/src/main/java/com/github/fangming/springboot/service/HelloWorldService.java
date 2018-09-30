package com.github.fangming.springboot.service;

import com.github.fangming.springboot.model.AboutMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldService {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldService.class);

    private final String welcomeMessage;
    private final AboutMe aboutMe;

    @Autowired
    public HelloWorldService(@Value("${welcomeMsg}") String welcomeMessage, AboutMe aboutMe) {
        this.welcomeMessage = welcomeMessage;
        this.aboutMe = aboutMe;
    }

    public String sayHello(){
        logger.info(welcomeMessage);
        return welcomeMessage + "\r\n" +
            AboutMe.getWelcomeMessage(aboutMe);
    }

}
