package com.sky7th.devtimeline.chat.repository;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {

  List<ChatMessage> findFirst30ByRoomId(String roomId);
}