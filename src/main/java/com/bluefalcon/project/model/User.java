package com.bluefalcon.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("user")
@Data
public class User {

    @Id
    private String id;
    private String emailId;
    private String name;
    private String location;
    private Long created;
    private Long updated;
    private String imageUrl;
    private List<String> userInterests;
    private List<UserSocial> socialProfile;
    private List<UserActivity> userActivity;
    private UserChat userChat;

    public User updateUser(User user) {
        return user;
        
    }

}
