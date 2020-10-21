package com.sky7th.devtimeline.chat.inMemory.chatUser.domain;

import java.util.Set;

public interface ChatUserRepositoryCustom {

  void updateChatRoomIds(String sessionId, Set<Long> chatRoomIds);
}
