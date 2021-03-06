package com.sky7th.devtimeline.chat.domain.chattingMessage.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingMessageRepository extends JpaRepository<ChattingMessage, Long> {

  @EntityGraph(attributePaths = "user")
  @Query("FROM ChattingMessage c where c.roomId = :roomId")
  Page<ChattingMessage> findByRoomId(Long roomId, Pageable pageable);

  @EntityGraph(attributePaths = "user")
  @Query("FROM ChattingMessage c where c.roomId = :roomId and c.id <= :start")
  Page<ChattingMessage> findByRoomIdAndStart(Long roomId, Pageable pageable, Long start);
}