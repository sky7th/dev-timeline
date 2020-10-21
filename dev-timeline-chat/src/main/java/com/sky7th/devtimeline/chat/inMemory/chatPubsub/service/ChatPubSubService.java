package com.sky7th.devtimeline.chat.inMemory.chatPubsub.service;

import com.sky7th.devtimeline.chat.domain.chattingRoom.service.ChattingRoomInternalService;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.domain.ChatMessage;
import com.sky7th.devtimeline.chat.inMemory.chatRoom.domain.ChatRoom;
import com.sky7th.devtimeline.chat.inMemory.chatRoom.service.ChatRoomService;
import com.sky7th.devtimeline.chat.inMemory.chatPubsub.pubsub.RedisPublisher;
import com.sky7th.devtimeline.chat.inMemory.chatPubsub.pubsub.RedisSubscriber;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatPubSubService {

  private final ChatRoomService chatRoomService;
  private final ChattingRoomInternalService chattingRoomInternalService;
  private final RedisMessageListenerContainer redisMessageListener;
  private final RedisPublisher redisPublisher;
  private final RedisSubscriber redisSubscriber;
  private Map<String, ChannelTopic> channels;

  @PostConstruct
  public void init() {
    channels = new HashMap<>();

    chattingRoomInternalService.findAll().stream()
        .map(ChatRoom::toEntity)
        .forEach(this::createTopic);
  }

  private void createTopic(ChatRoom chatRoom) {
    ChatRoom savedChatRoom = chatRoomService.save(chatRoom);
    ChannelTopic channel = new ChannelTopic(savedChatRoom.getId());
    redisMessageListener.addMessageListener(redisSubscriber, channel);
    channels.put(savedChatRoom.getId(), channel);
  }

  public void publish(ChatMessage chatMessage) {
    ChatRoom chatRoom = chatRoomService.findByRoomId(chatMessage.getRoomId());
    ChannelTopic channel = channels.get(chatRoom.getId());
    redisPublisher.publish(channel, chatMessage);
  }
}
