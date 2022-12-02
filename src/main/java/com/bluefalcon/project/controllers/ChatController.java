package com.bluefalcon.project.controllers;


import com.bluefalcon.project.model.Message;
import com.bluefalcon.project.model.UserChat;
import com.bluefalcon.project.response.BaseResponse;
import com.bluefalcon.project.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping(value = "/messages/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> sendMessage( @RequestBody Message message) {
        return ResponseEntity.ok(chatService.sendMessage(message));
    }

    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserChat> getMessages(@RequestParam (value = "userId") String userId) {
        return ResponseEntity.ok(chatService.getMessages(userId));
    }
}
