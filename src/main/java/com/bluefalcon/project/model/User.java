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
    private String firstName;
    private String lastName;
    private String location;
    private String gender;
    private String imageUrl;
    private List<String> userInterests;
    private List<String> followerUserIds;
    private List<String> followingUserIds;
    private List<String> friendUserIds;
    private List<String> blockedUserIds;
    private List<String> watchedPostIds;

    public User updateUser(User user) {
        return user;
    }

}
