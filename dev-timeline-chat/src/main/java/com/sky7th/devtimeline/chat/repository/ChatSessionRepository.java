package com.sky7th.devtimeline.chat.repository;

import com.sky7th.devtimeline.chat.model.ChatSession;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ChatSessionRepository extends CrudRepository<ChatSession, String>, ChatSessionRepositoryCustom {

  Optional<ChatSession> findByRoomIdAndUserId(Long roomId, Long userId);
  List<ChatSession> findAllByRoomId(Long roomId);
}