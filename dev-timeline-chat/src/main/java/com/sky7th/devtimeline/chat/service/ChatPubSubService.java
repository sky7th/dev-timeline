package com.sky7th.devtimeline.chat.service;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import com.sky7th.devtimeline.chat.model.ChatRoom;
import com.sky7th.devtimeline.chat.pubsub.RedisPublisher;
import com.sky7th.devtimeline.chat.pubsub.RedisSubscriber;
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
  private final RedisMessageListenerContainer redisMessageListener;
  private final RedisPublisher redisPublisher;
  private final RedisSubscriber redisSubscriber;
  private Map<String, ChannelTopic> channels;

  @PostConstruct
  public void init() {
//    chatRoomService.findAll();
    channels = new HashMap<>();
    ChatRoom chatRoom1 = chatRoomService.save("1");
    ChatRoom chatRoom2 = chatRoomService.save("2");
    channels.put(chatRoom1.getId(), new ChannelTopic(chatRoom1.getId()));
    channels.put(chatRoom2.getId(), new ChannelTopic(chatRoom2.getId()));
    redisMessageListener.addMessageListener(redisSubscriber, channels.get(chatRoom1.getId()));
    redisMessageListener.addMessageListener(redisSubscriber, channels.get(chatRoom2.getId()));
  }

  public ChatRoom createRoom(String roomName) {
    ChatRoom chatRoom = chatRoomService.save(roomName);
    ChannelTopic channel = new ChannelTopic(chatRoom.getId());
    redisMessageListener.addMessageListener(redisSubscriber, channel);
    channels.put(chatRoom.getId(), channel);

    return chatRoom;
  }

  public void deleteRoom(String roomId) {
    ChannelTopic channel = channels.get(roomId);
    redisMessageListener.removeMessageListener(redisSubscriber, channel);
    channels.remove(roomId);
//    chatRoomService.delete(roomId);
  }

  public void pushMessage(ChatMessage chatMessage) {
    ChannelTopic channel = channels.get(chatMessage.getRoomId());
    redisPublisher.publish(channel, chatMessage);
  }
}
