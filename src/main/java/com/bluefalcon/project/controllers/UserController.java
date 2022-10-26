package com.bluefalcon.project.controllers;

import com.bluefalcon.project.model.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1")
public class UserController {

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserDetails (@RequestParam ("emailId") String emailId){
        User user = new User();
        user.setEmail(emailId);
        return ResponseEntity.ok(user);

//         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> handleGoogleLogin (User user){
        return null;
    }

}
