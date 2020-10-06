package com.sky7th.devtimeline.chat.repository;

import com.sky7th.devtimeline.chat.model.ChatRoom;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, String>, ChatRoomRepositoryCustom {

  @Override
  List<ChatRoom> findAll();

  ChatRoom findByRoomId(Long roomId);
}