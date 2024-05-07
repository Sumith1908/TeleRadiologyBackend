package com.example.TeleRadiology.domain.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.TeleRadiology.data.dao.ChatDao;
import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.DoctorDao;
import com.example.TeleRadiology.data.dao.MessageDao;
import com.example.TeleRadiology.data.dao.PatientDao;
import com.example.TeleRadiology.data.dao.RadiologistDao;
import com.example.TeleRadiology.data.dao.ReportDao;
import com.example.TeleRadiology.data.entities.ChatEntity;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.DoctorEntity;
import com.example.TeleRadiology.data.entities.MessageEntity;
import com.example.TeleRadiology.data.entities.PatientEntity;
import com.example.TeleRadiology.data.entities.RadiologistEntity;
import com.example.TeleRadiology.data.entities.ReportEntity;
import com.example.TeleRadiology.domain.model.Message;
import com.example.TeleRadiology.dto.DocRes;
import com.example.TeleRadiology.dto.GetChatsResult;
import com.example.TeleRadiology.dto.PatientRes;
import com.example.TeleRadiology.dto.RadRes;
import com.example.TeleRadiology.exception.ChatNotFoundException;
import com.example.TeleRadiology.exception.GlobalException;
import com.example.TeleRadiology.exception.ReportNotFoundException;
import com.example.TeleRadiology.exception.UserNotFoundException;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
class Pair {
    int id;
    String role;
    int reportId;
}

@Data
class Pair1 {
    int id;
    String role;
}

@Component
@RequiredArgsConstructor
public class ChatRepositoryImplementation implements ChatRepository {
    private final ChatDao chatDao;
    private final MessageDao messageDao;
    private final CredentialsDao credDao;
    private final ReportDao repDao;
    private final DoctorDao docDao;
    private final PatientDao patDao;
    private final RadiologistDao radDao;

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

    @Override
    public GetChatsResult getChats(int userId) {
        List<ChatEntity> chats = chatDao.findAllByUser1IdIdOrUser2IdId(userId, userId).orElseThrow(
                () -> new UserNotFoundException("Invalid userID"));
        List<Pair> users = new ArrayList<>();
        HashSet<Pair1> uniqueUsers = new HashSet<>();
        for (ChatEntity chat : chats) {
            if (chat.getUser1Id().getId() == userId) {
                if (chat.getUser2Id().getActive() == 1) {
                    Pair pair = new Pair();
                    pair.id = chat.getUser2Id().getId();
                    pair.role = chat.getUser2Id().getRole().getRole();
                    pair.setReportId(chat.getReportId().getId());
                    users.add(pair);
                }
            } else {
                if (chat.getUser1Id().getActive() == 1) {
                    Pair pair = new Pair();
                    pair.id = chat.getUser1Id().getId();
                    pair.role = chat.getUser1Id().getRole().getRole();
                    pair.setReportId(chat.getReportId().getId());
                    users.add(pair);
                }
            }
        }

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (Pair pair : users) {
            List<Integer> list;
            if (map.containsKey(pair.getId()) == true) {
                list = map.get(pair.getId());
            } else {
                list = new ArrayList<>();
            }
            list.add(pair.getReportId());
            map.put(pair.getId(), list);
            Pair1 pair1 = new Pair1();
            pair1.setId(pair.getId());
            pair1.setRole(pair.getRole());
            uniqueUsers.add(pair1);
        }
        List<PatientRes> pats = new ArrayList<>();
        List<DocRes> docs = new ArrayList<>();
        List<RadRes> rads = new ArrayList<>();
        for (Pair1 pair1 : uniqueUsers) {
            if (pair1.getRole().equals("ROLE_DOCTOR")) {
                DoctorEntity doc = docDao.findByUserIdId(pair1.id).orElse(null);
                if (doc != null) {
                    DocRes res = new DocRes();
                    res.setUserId(doc.getUserId().getId());
                    String name="";
                    if(doc.getMiddleName()==null) {
                        name=doc.getFirstName()+" "+doc.getLastName();
                    }
                    else {
                        name=doc.getFirstName()+" "+doc.getMiddleName()+" "+doc.getLastName();
                    }
                     res.setName(name);
                     res.setReports(map.get(pair1.getId()));
                     docs.add(res);
                }
            }
            if (pair1.getRole().equals("ROLE_PATIENT")) {
                PatientEntity pat = patDao.findByUserIdId(pair1.id).orElse(null);
                if (pat != null) {
                    PatientRes res = new PatientRes();
                    res.setUserId(pat.getUserId().getId());
                    String name="";
                    if(pat.getMiddleName()==null) {
                        name=pat.getFirstName()+" "+pat.getLastName();
                    }
                    else {
                        name=pat.getFirstName()+" "+pat.getMiddleName()+" "+pat.getLastName();
                    }
                     res.setName(name);
                     res.setReports(map.get(pair1.getId()));
                     pats.add(res);
                }
            }
            if (pair1.getRole().equals("ROLE_RADIOLOGIST")) {
                RadiologistEntity rad = radDao.findByUserIdId(pair1.id).orElse(null);
                if (rad != null) {
                    RadRes res = new RadRes();
                    res.setUserId(rad.getUserId().getId());
                    String name="";
                    if(rad.getMiddleName()==null) {
                        name=rad.getFirstName()+" "+rad.getLastName();
                    }
                    else {
                        name=rad.getFirstName()+" "+rad.getMiddleName()+" "+rad.getLastName();
                    }
                     res.setName(name);
                     res.setReports(map.get(pair1.getId()));
                     rads.add(res);
                }
            }
        }
        GetChatsResult chatRes = new GetChatsResult();
        chatRes.setDocs(docs);
        chatRes.setPats(pats);
        chatRes.setRads(rads);
        return chatRes;
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