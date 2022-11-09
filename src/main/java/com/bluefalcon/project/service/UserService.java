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
        userDao.save(user);
        return user;
    }

    public User getUser(String emailId) {
        return userDao.findByEmailId(emailId);
    }

    public User updateUser(User user) {
        Query updateQuery = new Query();
        updateQuery.addCriteria(Criteria.where("id").is(user.getId()));
        Update update = new Update();
        update.set("firstName", user.getFirstName());
        update.set("lastName", user.getLastName());
        update.set("location", user.getLocation());
        update.set("gender", user.getGender());
        mongoTemplate.updateFirst(updateQuery, update, User.class);

        Query findQuery = new Query();
        findQuery.addCriteria(Criteria.where("id").is(user.getId()));
        User result = mongoTemplate.findOne(findQuery, User.class);
        return result;
    }
}
