package com.sky7th.devtimeline.chat.service;

import com.google.common.collect.Lists;
import com.sky7th.devtimeline.chat.model.ChatRoom;
import com.sky7th.devtimeline.chat.model.ChatUser;
import com.sky7th.devtimeline.chat.repository.ChatRoomRepository;
import com.sky7th.devtimeline.chat.service.exception.NotFoundChatRoomException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;
  private final ChatUserService chatUserService;

  @Transactional(readOnly = true)
  public ChatRoom findById(String roomId) {
    return chatRoomRepository.findById(roomId).orElseThrow(NotFoundChatRoomException::new);
  }

  public List<ChatRoom> findAll() {
    return Lists.newArrayList(chatRoomRepository.findAll());
  }

  public ChatRoom save(String roomName) {
    return chatRoomRepository.save(new ChatRoom(roomName));
  }

  public ChatRoom enter(String roomId, ChatUser chatUser) {
    Long userId = chatUser.getUserId();
    ChatRoom chatRoom = findById(roomId);
    Map<Long, Integer> chatUserSessionCountMap = chatRoom.getChatUserSessionCountMap();

    if (chatUserSessionCountMap.containsKey(userId)) {
      int sessionCount = chatUserSessionCountMap.get(userId);
      chatUserSessionCountMap.put(userId, sessionCount + 1);
    } else {
      chatUserSessionCountMap.put(userId, 1);
    }
    chatRoomRepository.updateChatUserIds(roomId, chatUserSessionCountMap);
    chatUserService.addChatRoomId(chatUser.getSessionId(), roomId);

    return chatRoom;
  }

  public ChatRoom exit(String roomId, ChatUser chatUser) {
    Long userId = chatUser.getUserId();
    ChatRoom chatRoom = findById(roomId);
    Map<Long, Integer> chatUserSessionCountMap = chatRoom.getChatUserSessionCountMap();
    int sessionCount = chatUserSessionCountMap.get(userId);

    if (sessionCount > 1) {
      chatUserSessionCountMap.put(userId, sessionCount - 1);
    } else {
      chatUserSessionCountMap.remove(userId);
    }
    chatRoomRepository.updateChatUserIds(roomId, chatUserSessionCountMap);
    chatUserService.removeChatRoomId(chatUser.getSessionId(), roomId);

    return chatRoom;
  }

  public void exitAllChatRoom(ChatUser chatUser) {
    chatUser.getChatRoomIds().forEach(roomId -> {
      exit(roomId, chatUser);
    });
  }
}
