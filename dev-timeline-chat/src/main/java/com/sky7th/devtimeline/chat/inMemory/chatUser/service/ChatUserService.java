package com.sky7th.devtimeline.chat.inMemory.chatUser.service;

import com.sky7th.devtimeline.chat.inMemory.chatUser.domain.ChatUser;
import com.sky7th.devtimeline.chat.inMemory.chatUser.domain.ChatUserRepository;
import com.sky7th.devtimeline.chat.inMemory.chatUser.exception.NotFoundChatUserException;
import com.sky7th.devtimeline.user.dto.UserContext;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatUserService {

  private final ChatUserRepository chatUserRepository;

  @Transactional(readOnly = true)
  public ChatUser findBySessionId(String sessionId) {
    return chatUserRepository.findById(sessionId).orElseThrow(NotFoundChatUserException::new);
  }

  public void addChatRoomId(String sessionId, Long roomId) {
    ChatUser chatUser = findBySessionId(sessionId);
    Set<Long> chatRoomIds = chatUser.getChatRoomIds();
    chatRoomIds.add(roomId);
    chatUserRepository.updateChatRoomIds(sessionId, chatRoomIds);
  }

  public void removeChatRoomId(String sessionId, Long roomId) {
    ChatUser chatUser = findBySessionId(sessionId);
    Set<Long> chatRoomIds = chatUser.getChatRoomIds();
    chatRoomIds.remove(roomId);
    chatUserRepository.updateChatRoomIds(sessionId, chatRoomIds);
  }

  public void save(String sessionId, UserContext userContext) {
    try {
      findBySessionId(sessionId);
    } catch (NotFoundChatUserException e) {
      chatUserRepository.save(ChatUser.of(userContext, sessionId));
    }
  }

  public void delete(String sessionId) {
    chatUserRepository.deleteById(sessionId);
  }
}
