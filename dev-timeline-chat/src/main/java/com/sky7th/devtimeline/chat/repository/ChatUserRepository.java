package com.sky7th.devtimeline.chat.repository;

import com.sky7th.devtimeline.chat.model.ChatUser;
import org.springframework.data.repository.CrudRepository;

public interface ChatUserRepository extends CrudRepository<ChatUser, String>, ChatUserRepositoryCustom {

  ChatUser findByUserId(Long userId);
}