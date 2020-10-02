package com.sky7th.devtimeline.chat.repository;

import com.sky7th.devtimeline.chat.model.ChatRoom;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, String>, ChatRoomRepositoryCustom {
}