package com.bluefalcon.project.service;

import com.bluefalcon.project.dao.UserDao;
import com.bluefalcon.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User addUser (User user){
        userDao.save(user);
        return user;
    }

    public User getUser (String emailId){
        User byEmailId = userDao.findByEmailId(emailId);
        return byEmailId;
    }
}
