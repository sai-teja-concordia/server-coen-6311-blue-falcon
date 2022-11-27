package com.bluefalcon.project.dao;

import com.bluefalcon.project.model.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityDao extends MongoRepository<UserActivity, String> {
}
