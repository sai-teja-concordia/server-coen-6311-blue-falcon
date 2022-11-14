package com.bluefalcon.project.service;

import com.bluefalcon.project.dao.UserDao;
import com.bluefalcon.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    MongoTemplate mongoTemplate;

    public User addUser(User user) {
        user.setCreated(System.currentTimeMillis());
        user.setUpdated(System.currentTimeMillis());
        userDao.save(user);
        return user;
    }

    public Boolean addFavouriteTopic(User user){
        Query fetchQuery = new Query();
        fetchQuery.addCriteria(Criteria.where("emailId").is(user.getEmailId()));
        Update update = new Update();
        update.set("userInterests", user.getUserInterests());
        mongoTemplate.updateFirst(fetchQuery, update, User.class);
        return true;
    }

    public User getUser(String emailId) {
        return userDao.findByEmailId(emailId);
    }

    public User updateUser(User user) {
        Query updateQuery = new Query();
        updateQuery.addCriteria(Criteria.where("emailId").is(user.getEmailId()));
        Update update = new Update();
        if (user.getName() != null) {
            update.set("name", user.getName());
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
        return user;
    }


}
