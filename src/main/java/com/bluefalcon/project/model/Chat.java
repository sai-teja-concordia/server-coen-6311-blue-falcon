package com.bluefalcon.project.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("chat")
@Data
public class Chat {

    private String id;

    private String fromUser;

    private String receiverUser;

    private List<String> messages;

}
