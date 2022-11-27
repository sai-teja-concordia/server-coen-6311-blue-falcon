package com.bluefalcon.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("user_chat")
@Data
public class UserChat {

    @Id
    private String id;

    private String userId;

    private List<String> chats;
}
