package com.example.TeleRadiology.domain.repositories;

import java.util.List;

import com.example.TeleRadiology.domain.model.Message;
import com.example.TeleRadiology.dto.GetChatsResult;

public interface ChatRepository {
    public int getChatId(int user1, int user2, int reportId);

    public List<Message> getMessages(int chatId);

    public void addChat(int user1Id, int user2Id, int reportId);

    public void addMessage(int sender, int chatId, String message);

    public GetChatsResult getChats(int userId);
}
