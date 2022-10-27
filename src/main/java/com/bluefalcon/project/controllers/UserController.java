package com.bluefalcon.project.controllers;

import com.bluefalcon.project.model.User;
import com.bluefalcon.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserDetails (@RequestParam ("emailId") String emailId){
        return ResponseEntity.ok(userService.getUser(emailId));
    }


    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser (@RequestBody User user){
        return ResponseEntity.ok(userService.addUser(user));
    }

}