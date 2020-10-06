package com.sky7th.devtimeline.chat.service;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import com.sky7th.devtimeline.chat.model.ChatRoom;
import com.sky7th.devtimeline.chat.pubsub.RedisPublisher;
import com.sky7th.devtimeline.chat.pubsub.RedisSubscriber;
import com.sky7th.devtimeline.chat.service.event.OnGeneratePushMessageEvent;
import com.sky7th.devtimeline.core.domain.chattingRoom.service.ChattingRoomInternalService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.annotation.Async;
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
        .map(chattingRoom -> ChatRoom.toEntity(chattingRoom, new HashMap<>()))
        .forEach(this::createRoom);
  }

  private void createRoom(ChatRoom chatRoom) {
    ChatRoom savedChatRoom = chatRoomService.save(chatRoom);
    ChannelTopic channel = new ChannelTopic(savedChatRoom.getId());
    redisMessageListener.addMessageListener(redisSubscriber, channel);
    channels.put(savedChatRoom.getId(), channel);
  }

  public void deleteRoom(String roomId) {
    ChannelTopic channel = channels.get(roomId);
    redisMessageListener.removeMessageListener(redisSubscriber, channel);
    channels.remove(roomId);
  }

  public void publish(ChatMessage chatMessage) {
    ChannelTopic channel = channels.get(chatMessage.getRoomId());
    redisPublisher.publish(channel, chatMessage);
  }

  @Async
  @EventListener
  public void onPublishEvent(OnGeneratePushMessageEvent onGenerateEmailVerificationEvent) {
    publish(onGenerateEmailVerificationEvent.getChatMessage());
  }
}
