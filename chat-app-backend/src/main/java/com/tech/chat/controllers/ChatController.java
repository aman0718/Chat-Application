package com.tech.chat.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tech.chat.entities.Message;
import com.tech.chat.entities.Room;
import com.tech.chat.payload.MessageRequest;
import com.tech.chat.repositories.RoomRepository;

@RestController
@CrossOrigin("http://localhost:5173")
public class ChatController {

    @Autowired
    private RoomRepository roomRepository;

    @MessageMapping("/sendMessage/{roomId}")    // '/app/sendMessage/roomId'
    @SendTo("/topic/room/{roomId}") // subscribe
    public Message sendMessage(@DestinationVariable String roomId, @RequestBody MessageRequest request) {

        // get room using room_id
        Room room = roomRepository.findByRoomId(request.getRoomId());

        // add message to room
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());

        // save room
        if (room != null) {
            room.getMessages().add(message);
            roomRepository.save(room);
        }else{
            throw new RuntimeException("Room not found");
        }

        // return message
        return message;
    }
}
