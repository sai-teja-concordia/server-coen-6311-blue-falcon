package com.bluefalcon.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class User {

    @Id
    private String id;
    private String email;
    private String familyName;
    private String givenName;
    private String imageUrl;
    private String location;

    private List<String> userInterests;
    private List<String> followerUserIds;
    private List<String> followingUserIds;
    private List<String> friendUserIds;
    private List<String> watchedPostIds;

    public User addUser (User user){
        return user;
    }

    public User getUser (String emailId){
        return null;
    }

    public User updateUser (User user){
        return user;
    }
    
    public List<User> getUsersInCountry (String country){
        return null;
    }

    public List<User> getFollowingUsers (){
        return null;
    }

    public List<User> getFollowerUsers (){
        return null;
    }

    public List<User> getFriends (){
        return null;
    }

    public List<String> getUserInterests(){
        return null;
    }

    public List<String> saveUserInterests(){
        return null;
    }

}
