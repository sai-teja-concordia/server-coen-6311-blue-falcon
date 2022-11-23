package com.bluefalcon.project.dao;

import com.bluefalcon.project.model.UserChat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatDao  extends MongoRepository<UserChat, String> {
}
