package com.example.TeleRadiology.domain.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.TeleRadiology.data.dao.ChatDao;
import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.MessageDao;
import com.example.TeleRadiology.data.dao.ReportDao;
import com.example.TeleRadiology.data.entities.ChatEntity;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.MessageEntity;
import com.example.TeleRadiology.data.entities.ReportEntity;
import com.example.TeleRadiology.domain.model.Message;
import com.example.TeleRadiology.exception.ChatNotFoundException;
import com.example.TeleRadiology.exception.GlobalException;
import com.example.TeleRadiology.exception.ReportNotFoundException;
import com.example.TeleRadiology.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRepositoryImplementation implements ChatRepository {
    private final ChatDao chatDao;
    private final MessageDao messageDao;
    private final CredentialsDao credDao;
    private final ReportDao repDao;

    @Override
    public int getChatId(int user1, int user2, int reportId) {
        ChatEntity chatEnt = chatDao.findByUser1IdIdAndUser2IdIdAndReportIdId(user1, user2, reportId).orElseThrow(
                () -> new ChatNotFoundException("No chat Between these 2"));

        return chatEnt.getId();
    }

    @Override
    public List<Message> getMessages(int chatId) {
        List<MessageEntity> messages = messageDao.findAllByChatIdId(chatId).orElseThrow(
                () -> new GlobalException("Wrong Chat  Id"));

        return mapAllToDomainMessageEntity(messages);
    }

    @Override
    public void addMessage(int sender, int chatId, String message) {
        MessageEntity messageEnt = new MessageEntity();
        ChatEntity chat = chatDao.findById(chatId).orElseThrow(
                () -> new ChatNotFoundException("No chat exists"));
        CredentialsEntity user1 = credDao.findById(sender).orElseThrow(
                () -> new UserNotFoundException("Wrong sender"));
        LocalDateTime time = LocalDateTime.now();
        messageEnt.setChatId(chat);
        messageEnt.setMessage(message);
        messageEnt.setSender(user1);
        messageEnt.setTimeStamp(time);

        messageDao.save(messageEnt);
    }

    @Override
    public void addChat(int user1Id, int user2Id, int reportId) {
        ChatEntity chatEnt = new ChatEntity();
        ReportEntity report = repDao.findById(reportId).orElseThrow(
                () -> new ReportNotFoundException("No such report"));
        CredentialsEntity user1 = credDao.findById(user1Id).orElseThrow(
                () -> new UserNotFoundException("No such user"));

        CredentialsEntity user2 = credDao.findById(user2Id).orElseThrow(
                () -> new UserNotFoundException("No such user"));

        chatEnt.setReportId(report);
        chatEnt.setUser1Id(user1);
        chatEnt.setUser2Id(user2);
        chatDao.save(chatEnt);
    }

    private List<Message> mapAllToDomainMessageEntity(List<MessageEntity> messages) {
        List<Message> allMessages = new ArrayList<>();

        for (MessageEntity messageEntity : messages) {
            allMessages.add(mapToDomainMessageEntity(messageEntity));
        }
        return allMessages;
    }

    private Message mapToDomainMessageEntity(MessageEntity messageEntity) {
        Message message = new Message();
        message.setChatId(messageEntity.getChatId().getId());
        message.setMessage(messageEntity.getMessage());
        message.setSender(messageEntity.getSender().getId());
        message.setTimeStamp(messageEntity.getTimeStamp());
        return message;
    }
}
