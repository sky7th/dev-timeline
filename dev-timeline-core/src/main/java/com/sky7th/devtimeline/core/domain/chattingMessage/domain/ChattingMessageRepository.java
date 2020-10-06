package com.sky7th.devtimeline.core.domain.chattingMessage.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingMessageRepository extends JpaRepository<ChattingMessage, String> {

  @EntityGraph(attributePaths = "user")
  @Query("FROM ChattingMessage c")
  Page<ChattingMessage> findAllByRoomID(Long roomId, Pageable pageable);
}