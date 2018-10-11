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
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>((User) null, HttpStatus.NOT_FOUND));
    }

    //TODO, will dig out to find a best way to handing exception
    @ApiOperation(value = "Add a user")
    @PostMapping(value = "/add",  produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User persistedUser = userRepository.save(user);
        return new ResponseEntity<>(persistedUser, HttpStatus.OK);
    }

    @ApiOperation(value = "Update a user")
    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User storedUser = userOptional.get();
            storedUser.setDateOfBirth(user.getDateOfBirth());
            storedUser.setUserName(user.getUserName());
            storedUser.setReputation(user.getReputation());
            storedUser.setSex(user.getSex());
            storedUser.setEnabled(user.isEnabled());
            User persistedUser = userRepository.save(storedUser);
            return new ResponseEntity<>(persistedUser, HttpStatus.OK);
        }else {
            return new ResponseEntity<>((User)null, HttpStatus.NOT_FOUND);
        }
    }
}
