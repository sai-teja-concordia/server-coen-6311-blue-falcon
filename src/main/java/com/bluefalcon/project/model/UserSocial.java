package com.bluefalcon.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("user_social")
@Data
public class UserSocial {

    @Id
    private String id;

    private String userId;

    private List<String> friends = new ArrayList<>();

    private List<String> sentFriendRequests = new ArrayList<>();

    private List<String> receivedFriendRequests = new ArrayList<>();

    private List<String> followerUsers = new ArrayList<>();

    private List<String> followingUsers = new ArrayList<>();

    private List<String> blockedUsers = new ArrayList<>();

}
