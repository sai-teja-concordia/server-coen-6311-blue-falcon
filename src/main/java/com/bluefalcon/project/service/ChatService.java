package com.bluefalcon.project.service;

import com.bluefalcon.project.dao.MessageDao;
import com.bluefalcon.project.model.Chat;
import com.bluefalcon.project.model.Message;
import com.bluefalcon.project.model.UserChat;
import com.bluefalcon.project.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    MessageDao messageDao;

    public BaseResponse sendMessage(Message message) {
        if (message.getFromSender() == null || message.getToSender() == null || message.getContent() == null){
            return BaseResponse.builder().build();
        }
        message.setSentTime(System.currentTimeMillis());
        messageDao.save(message);
        return BaseResponse.builder().build();
    }

    public UserChat getMessages(String userId) {
        List<Message> fromMessages = messageDao.findByFromSender(userId);
        Map<String, List<Message>> toSenderToMessageMap = fromMessages.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(Message::getToSender));
        List<Message> toMessages = messageDao.findByToSender(userId);
        Map<String, List<Message>> fromSenderToMessageMap = toMessages.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(Message::getFromSender));
        fromSenderToMessageMap.forEach(
                (key, value) -> toSenderToMessageMap.merge(key, value, (v1, v2) ->{
                    v1.addAll(v2);
                    return v1;
                }));

        List<Chat> chats = new ArrayList<>();
        toSenderToMessageMap.forEach((key, value) -> {
            value.sort(Comparator.comparing(Message::getSentTime));
            Chat chat = Chat.builder().messages(value).fromUser(key).receiverUser(userId).build();
            chats.add(chat);
        });

        return UserChat.builder()
                .userId(userId)
                .chats(chats)
                .build();
    }
}
