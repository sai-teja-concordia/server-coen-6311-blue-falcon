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
        user.setCreated(System.currentTimeMillis());
        user.setUpdated(System.currentTimeMillis());
        userDao.save(user);
        return user;
    }

    public User getUser (String emailId){
        return userDao.findByEmailId(emailId);
    }
}
