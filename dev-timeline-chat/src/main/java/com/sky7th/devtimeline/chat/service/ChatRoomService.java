package com.sky7th.devtimeline.chat.service;

import com.google.common.collect.Lists;
import com.sky7th.devtimeline.chat.model.ChatRoom;
import com.sky7th.devtimeline.chat.repository.ChatRoomRepository;
import com.sky7th.devtimeline.chat.service.exception.AlreadyDeletedChatRoomException;
import com.sky7th.devtimeline.chat.service.exception.NotFoundChatRoomException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;

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

  public void delete(String roomId) {
    if (!chatRoomRepository.existsById(roomId)) {
      throw new AlreadyDeletedChatRoomException();
    }
    chatRoomRepository.existsById(roomId);
  }

  public void addChatUserId(String roomId, Long userId) {
    ChatRoom chatRoom = findById(roomId);
    Set<Long> userIds = chatRoom.getChatUserIds();
    userIds.add(userId);
    chatRoomRepository.updateChatUserIds(roomId, userIds);
  }

  public void removeChatUserId(String roomId, Long userId) {
    ChatRoom chatRoom = findById(roomId);
    Set<Long> userIds = chatRoom.getChatUserIds();
    userIds.remove(userId);
    chatRoomRepository.updateChatUserIds(roomId, userIds);
  }
}
