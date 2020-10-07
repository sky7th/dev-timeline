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
  public void updateChatRoomIds(String sessionId, Set<Long> chatRoomIds) {
    redisKeyValueTemplate.update(new PartialUpdate<>(sessionId, ChatUser.class)
        .set("chatRoomIds", Lists.newArrayList(chatRoomIds)));
  }
}
