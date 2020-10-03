package com.sky7th.devtimeline.chat.repository;

import com.sky7th.devtimeline.chat.model.ChatRoom;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.RedisKeyValueTemplate;

@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

  private final RedisKeyValueTemplate redisKeyValueTemplate;

  @Override
  public void updateChatUserIds(String roomId, Map<Long, Integer> chatUserSessionCountMap) {
    redisKeyValueTemplate.update(new PartialUpdate<>(roomId, ChatRoom.class)
        .set("chatUserSessionCountMap", chatUserSessionCountMap));
  }
}
