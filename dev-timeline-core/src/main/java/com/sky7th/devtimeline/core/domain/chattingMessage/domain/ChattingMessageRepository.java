package com.sky7th.devtimeline.core.domain.chattingMessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingMessageRepository extends JpaRepository<ChattingMessage, String> {
}