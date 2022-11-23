package com.bluefalcon.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document("user_social")
@Data
public class UserSocial {

    @Id
    private String id;

    private String userId;

    private Set<String> friends = new HashSet<>();

    private Set<String> sentFriendRequests = new HashSet<>();

    private Set<String> receivedFriendRequests = new HashSet<>();

    private Set<String> followerUsers = new HashSet<>();

    private Set<String> followingUsers = new HashSet<>();

    private Set<String> blockedUsers = new HashSet<>();

}
