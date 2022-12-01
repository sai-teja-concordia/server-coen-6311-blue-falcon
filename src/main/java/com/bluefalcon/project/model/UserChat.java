package com.bluefalcon.project.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("user_chat")
@Data
@Builder
public class UserChat {

    @Id
    private String id;

    private String userId;

    private List<Chat> chats;
}
