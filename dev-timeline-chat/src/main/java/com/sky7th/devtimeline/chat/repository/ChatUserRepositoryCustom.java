package com.sky7th.devtimeline.chat.repository;

import java.util.Set;

public interface ChatUserRepositoryCustom {

  void updateChatRoomIds(String sessionId, Set<String> chatRoomIds);
}
