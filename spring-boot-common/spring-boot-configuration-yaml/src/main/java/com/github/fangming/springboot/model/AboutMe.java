package com.github.fangming.springboot.model;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "me")
public class AboutMe {

    private String name;
    private String title;
    private String email;
    private String mobile;
    private List<String> address;
    private String selfIntruduction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public String getSelfIntruduction() {
        return selfIntruduction;
    }

    public void setSelfIntruduction(String selfIntruduction) {
        this.selfIntruduction = selfIntruduction;
    }

    public static String getWelcomeMessage(AboutMe aboutMe) {
        return String.format("<h3>%s </h3> " +
                "<ul> <li>Job: %s </li><li> Email: %s </li> <li> Mobile: %s </li><li> Address: %s </li><li> %s </li></ul>", aboutMe.getName(), aboutMe.getTitle(), aboutMe.getEmail(), aboutMe.getMobile(),
            aboutMe.getAddress(), aboutMe.getSelfIntruduction());
    }
}
