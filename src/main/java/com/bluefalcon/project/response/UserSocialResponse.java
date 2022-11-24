package com.bluefalcon.project.response;

import com.bluefalcon.project.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserSocialResponse {

    private List<User> friends;

    private List<User> followers;

    private List<User> following;

    private List<User> blocked;

}
