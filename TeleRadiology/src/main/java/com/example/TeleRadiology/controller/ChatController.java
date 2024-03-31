package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.services.ChatService;
import com.example.TeleRadiology.dto.AddChatReq;
import com.example.TeleRadiology.dto.AddMessageReq;
import com.example.TeleRadiology.dto.ChatReq;
import com.example.TeleRadiology.dto.MessagesResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/getMessages")
    public MessagesResult getReportsOfPatients(@RequestBody ChatReq chatReq) {
        MessagesResult messages = new MessagesResult();
        messages.setMessages(
                chatService.getMessages(chatReq.getUser1Id(), chatReq.getUser2Id(), chatReq.getReportId()));
        return messages;
    }

    @PostMapping("/createChat")
    public Boolean createChat(@RequestBody AddChatReq req) {
        chatService.addChat(req.getUser1Id(), req.getUser2Id(), req.getReportId());
        return true;
    }

    @PostMapping("/addMessage")
    public Boolean addMessage(@RequestBody AddMessageReq req) {
        chatService.addMessage(req.getSender(), req.getReciever(), req.getReport(), req.getMessage());
        return true;
    }

}
