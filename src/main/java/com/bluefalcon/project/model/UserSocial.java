package com.bluefalcon.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("user_social")
@Data
public class UserSocial {

    @Id
    private String id;

    private List<String> friends;

    private List<String> followerUsers;

    private List<String> followingUsers;

    private List<String> blockedUsers;

}
