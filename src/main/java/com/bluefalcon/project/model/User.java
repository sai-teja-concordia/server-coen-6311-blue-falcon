package com.bluefalcon.project.model;

import lombok.Data;
import com.bluefalcon.project.model.News;
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
    private List<User> friends;
    private List<User> followers;
    private List<User> following;
    private List<User> blocked;
    private List<User> sentFriendRequests;
    private List<News> savedNews;
    private List<News> wishlist;

    public User updateUser(User user) {
        return user;
        
    }

}
