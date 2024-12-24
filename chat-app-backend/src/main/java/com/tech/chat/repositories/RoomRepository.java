package com.tech.chat.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tech.chat.entities.Room;

public interface RoomRepository extends MongoRepository<Room, String> {

    // get room using room_id
    Room findByRoomId(String roomId);
}
