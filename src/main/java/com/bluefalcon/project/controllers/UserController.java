package com.bluefalcon.project.controllers;

import com.bluefalcon.project.model.User;
import com.bluefalcon.project.request.FriendRequest;
import com.bluefalcon.project.response.BaseResponse;
import com.bluefalcon.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    UserService userService;
    
  @PostMapping(value = "/add-fav-topic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addFavouriteTopics(@RequestBody User user) {
        return ResponseEntity.ok(userService.addFavouriteTopic(user));
    }

   
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserDetails(@RequestParam("emailId") String emailId) {
        return ResponseEntity.ok(userService.getUser(emailId));
    }

    @GetMapping(value = "/users/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUserFriends(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(userService.getUserFriends(userId));
    }

    @PostMapping(value = "/users/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> sendFriendRequest(@RequestBody FriendRequest friendRequest) {
        return ResponseEntity.ok(userService.sendingFriendRequest(friendRequest));
    }

    @PatchMapping(value = "/users/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> actionFriendRequest(@RequestBody FriendRequest friendRequest) {
        return ResponseEntity.ok(userService.actionFriendRequest(friendRequest));
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PatchMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody User payload) {
        return ResponseEntity.ok(userService.updateUser(payload));
    }


}
