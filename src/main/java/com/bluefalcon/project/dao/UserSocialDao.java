package com.bluefalcon.project.dao;

import com.bluefalcon.project.model.UserSocial;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSocialDao extends MongoRepository<UserSocial, String> {
    UserSocial findByUserId(String userId);
}

