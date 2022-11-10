package com.bluefalcon.project.service;

import com.bluefalcon.project.dao.UserDao;
import com.bluefalcon.project.exception.InvalidPayload;
import com.bluefalcon.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public User addUser(User user) {
        userDao.save(user);
        return user;
    }

    public User getUser(String emailId) {
        return userDao.findByEmailId(emailId);
    }

    public Boolean updateUser(User user) {
        if (user.getEmailId() == null) {
            throw new InvalidPayload();
        }
        Query updateQuery = new Query();
        updateQuery.addCriteria(Criteria.where("emailId").is(user.getEmailId()));
        Update update = new Update();
        if (user.getFamilyName() != null) {
            update.set("familyName", user.getFamilyName());
        }
        if (user.getGivenName() != null) {
            update.set("givenName", user.getGivenName());
        }
        if (user.getLocation() != null) {
            update.set("location", user.getLocation());
        }
        if (user.getImageUrl() != null) {
            update.set("imageUrl", user.getImageUrl());
        }
        if (user.getUserInterests() != null) {
            update.set("userInterests", user.getUserInterests());
        }
        mongoTemplate.updateFirst(updateQuery, update, User.class);
        return true;
    }
}
