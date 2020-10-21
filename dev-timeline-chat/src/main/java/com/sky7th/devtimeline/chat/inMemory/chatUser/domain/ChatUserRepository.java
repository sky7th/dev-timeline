package com.sky7th.devtimeline.chat.inMemory.chatUser.domain;

import org.springframework.data.repository.CrudRepository;

public interface ChatUserRepository extends CrudRepository<ChatUser, String>, ChatUserRepositoryCustom {
}