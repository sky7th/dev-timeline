package com.sky7th.devtimeline.chat.inMemory.chatSession.domain;

import java.util.Set;

public interface ChatSessionRepositoryCustom {

  void updateSessionIds(String id, Set<String> chatRoomIds);
}
