package com.sky7th.devtimeline.chat.pubsub;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {

  private final RedisTemplate<String, Object> redisTemplate;

  public void publish(ChannelTopic topic, ChatMessage message) {
    redisTemplate.convertAndSend(topic.getTopic(), message);
  }
}