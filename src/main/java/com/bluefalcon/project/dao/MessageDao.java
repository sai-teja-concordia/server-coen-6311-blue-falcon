package com.bluefalcon.project.dao;

import com.bluefalcon.project.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao  extends MongoRepository<Message, String> {
    List<Message> findByFromSender(String userId);

    List<Message> findByToSender(String userId);
}
