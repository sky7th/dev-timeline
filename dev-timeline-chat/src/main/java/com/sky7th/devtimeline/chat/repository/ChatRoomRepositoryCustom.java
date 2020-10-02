package com.sky7th.devtimeline.chat.repository;

import java.util.Set;

public interface ChatRoomRepositoryCustom {

  void updateChatUserIds(String roomId, Set<Long> chatUserIds);
}
