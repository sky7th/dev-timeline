package com.sky7th.devtimeline.chat.inMemory.chatSession.domain;

import com.google.common.collect.Lists;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.RedisKeyValueTemplate;

@RequiredArgsConstructor
public class ChatSessionRepositoryCustomImpl implements ChatSessionRepositoryCustom {

  private final RedisKeyValueTemplate redisKeyValueTemplate;

  @Override
  public void updateSessionIds(String id, Set<String> sessionIds) {
    redisKeyValueTemplate.update(new PartialUpdate<>(id, ChatSession.class)
        .set("sessionIds", Lists.newArrayList(sessionIds)));
  }
}
