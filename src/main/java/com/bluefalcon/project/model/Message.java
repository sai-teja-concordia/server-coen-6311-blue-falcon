package com.bluefalcon.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("message")
@Data
public class Message {

    @Id
    private String id;

    private String content;

    private String attachedFile;

    private String fromSender;

    private String toSender;

    private Long sentTime;

    private Long seenTime;

    private Long deliveredTime;

}
