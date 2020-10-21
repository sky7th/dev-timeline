package com.sky7th.devtimeline.chat.inMemory.chatMessage.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {

  @Override
  List<ChatMessage> findAll();
  List<ChatMessage> findAllByRoomId(Long roomId);
}