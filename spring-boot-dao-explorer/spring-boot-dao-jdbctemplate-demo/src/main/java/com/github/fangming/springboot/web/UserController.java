package com.github.fangming.springboot.web;

import com.github.fangming.springboot.model.User;
import com.github.fangming.springboot.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "list", produces = "application/json")
    public List<User> list(Model model){
        return userRepository.findAll();
    }

    @GetMapping(value = "/show/{id}", produces = "application/json")
    public ResponseEntity<User> showUser(@PathVariable Long id, Model model){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //TODO, will dig out to find a best way to handing exception
    @ApiOperation(value = "Add a user")
    @PostMapping(value = "/add",  produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User persistedUser = userRepository.insert(user);
        return ResponseEntity.ok(persistedUser);
    }

    @ApiOperation(value = "Update a user")
    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map((u) -> {
            u.setDateOfBirth(user.getDateOfBirth());
            u.setUserName(user.getUserName());
            u.setReputation(user.getReputation());
            u.setSex(user.getSex());
            u.setEnabled(user.isEnabled());
            return ResponseEntity.ok(userRepository.update(u));
        }).orElse(ResponseEntity.notFound().build());
    }
}
