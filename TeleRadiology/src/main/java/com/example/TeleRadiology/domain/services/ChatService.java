package com.example.TeleRadiology.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.TeleRadiology.domain.repositories.ChatRepository;
import com.example.TeleRadiology.dto.DocRes;
import com.example.TeleRadiology.dto.GetChatsResult;
import com.example.TeleRadiology.dto.PatientRes;
import com.example.TeleRadiology.dto.ProfilePicDTO;
import com.example.TeleRadiology.dto.RadRes;
import com.example.TeleRadiology.domain.model.Message;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepo;
    private final ImageService imgService;

    public List<Message> getMessages(int user1, int user2, int reportId) {
        if (user1 > user2) {
            int temp = user1;
            user1 = user2;
            user2 = temp;
        }
        int chatId = chatRepo.getChatId(user1, user2, reportId);
        return chatRepo.getMessages(chatId);
    }

    public void addChat(int user1, int user2, int rportId) {
        if (user1 > user2) {
            int temp = user1;
            user1 = user2;
            user2 = temp;
        }
        chatRepo.addChat(user1, user2, rportId);
    }

    public void addMessage(int sender, int reciever, int reportId, String message) {
        int user1 = Math.min(reciever, sender);
        int user2 = Math.max(reciever, sender);
        int chatId = chatRepo.getChatId(user1, user2, reportId);
        chatRepo.addMessage(sender, chatId, message);
    }

    public GetChatsResult getChats(int userId) {

        GetChatsResult chats = chatRepo.getChats(userId);
        // image req commented untill req
        // for (PatientRes pat : chats.getPats()) {
        // ProfilePicDTO dto = imgService.callImageServerGet("/getProfilePic/" +
        // Integer.toString(pat.getUserId()),
        // ProfilePicDTO.class);
        // pat.setProfileImage(dto.getProfilePic());
        // }
        // for (DocRes doc : chats.getDocs()) {
        // ProfilePicDTO dto = imgService.callImageServerGet("/getProfilePic/" +
        // Integer.toString(doc.getUserId()),
        // ProfilePicDTO.class);
        // doc.setProfileImage(dto.getProfilePic());
        // }
        // for (RadRes rad : chats.getRads()) {
        // ProfilePicDTO dto = imgService.callImageServerGet("/getProfilePic/" +
        // Integer.toString(rad.getUserId()),
        // ProfilePicDTO.class);
        // rad.setProfileImage(dto.getProfilePic());
        // }
        return chats;
    }
}
