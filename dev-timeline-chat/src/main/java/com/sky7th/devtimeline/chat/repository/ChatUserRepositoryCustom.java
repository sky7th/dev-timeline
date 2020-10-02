package com.sky7th.devtimeline.chat.repository;

import java.util.Set;

public interface ChatUserRepositoryCustom {

  void updateChatRoomIds(Long userId, Set<String> chatRoomIds);
  void updateSessionId(Long userId, String sessionId);
}
