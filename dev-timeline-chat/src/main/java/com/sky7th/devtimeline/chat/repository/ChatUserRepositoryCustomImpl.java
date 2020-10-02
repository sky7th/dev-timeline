package com.sky7th.devtimeline.chat.repository;

import com.google.common.collect.Lists;
import com.sky7th.devtimeline.chat.model.ChatUser;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.RedisKeyValueTemplate;

@RequiredArgsConstructor
public class ChatUserRepositoryCustomImpl implements ChatUserRepositoryCustom {

  private final RedisKeyValueTemplate redisKeyValueTemplate;

  @Override
  public void updateChatRoomIds(Long userId, Set<String> chatRoomIds) {
    redisKeyValueTemplate.update(new PartialUpdate<>(userId.toString(), ChatUser.class)
        .set("chatRoomIds", Lists.newArrayList(chatRoomIds)));
  }

  @Override
  public void updateSessionId(Long userId, String sessionId) {
    redisKeyValueTemplate.update(new PartialUpdate<>(userId.toString(), ChatUser.class)
        .set("sessionId", sessionId));
  }
}
