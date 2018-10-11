package com.github.fangming.springboot.web;

import com.github.fangming.springboot.model.User;
import com.github.fangming.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping(value = "list", produces = "json/")
    public List<User> list(Model model){
        return userRepository.findAll();
    }

    @GetMapping(value = "/show/{id}", produces = "application/json")
    public ResponseEntity<User> showUser(@PathVariable Long id, Model model){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity(null, HttpStatus.NOT_FOUND));
    }
}
