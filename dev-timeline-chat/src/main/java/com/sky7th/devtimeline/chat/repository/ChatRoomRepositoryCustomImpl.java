package com.sky7th.devtimeline.chat.repository;

import com.sky7th.devtimeline.chat.model.ChatRoom;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.RedisKeyValueTemplate;

@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

  private final RedisKeyValueTemplate redisKeyValueTemplate;

  @Override
  public void updateChatUserIds(String roomId, Set<Long> chatUserIds) {
    redisKeyValueTemplate.update(new PartialUpdate<>(roomId, ChatRoom.class)
        .set("chatUserIds", chatUserIds));
  }
}
