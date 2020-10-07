package com.sky7th.devtimeline.chat.repository;

import com.sky7th.devtimeline.chat.model.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, String> {

  @Override
  List<ChatRoom> findAll();
  Optional<ChatRoom> findByRoomId(Long roomId);
}