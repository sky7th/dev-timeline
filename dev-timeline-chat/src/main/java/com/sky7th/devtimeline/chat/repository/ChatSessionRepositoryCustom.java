package com.sky7th.devtimeline.chat.repository;

import java.util.Set;

public interface ChatSessionRepositoryCustom {

  void updateSessionIds(String id, Set<String> chatRoomIds);
}
