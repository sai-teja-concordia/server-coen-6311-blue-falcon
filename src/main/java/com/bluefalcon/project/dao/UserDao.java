package com.bluefalcon.project.dao;

import com.bluefalcon.project.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends MongoRepository<User, String> {

    User findByEmailId(String emailId);

    List<User> findByNameLike(String query);
}
