package com.example.TeleRadiology.controller;

import com.example.TeleRadiology.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.services.ChatService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teleRadiology")
// @CrossOrigin(originPatterns = "*localhost*")
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

    @GetMapping("/getChats/{id}")
    public GetChatsResult getChats(@PathVariable int id) {
        GetChatsResult res = chatService.getChats(id);

        return res;
    }

}