package com.sky7th.devtimeline.chat.repository;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {

  @Override
  List<ChatMessage> findAll();
  List<ChatMessage> findAllByRoomId(String roomId);
}