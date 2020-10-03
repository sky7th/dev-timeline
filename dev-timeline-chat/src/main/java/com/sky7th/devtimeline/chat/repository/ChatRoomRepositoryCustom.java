package com.sky7th.devtimeline.chat.repository;

import java.util.Map;

public interface ChatRoomRepositoryCustom {

  void updateChatUserIds(String roomId, Map<Long, Integer> chatUserSessionCountMap);
}
