package com.sky7th.devtimeline.chat.service;

import com.sky7th.devtimeline.chat.config.security.UserContext;
import com.sky7th.devtimeline.chat.model.ChatUser;
import com.sky7th.devtimeline.chat.repository.ChatUserRepository;
import com.sky7th.devtimeline.chat.service.exception.NotFoundChatUserException;
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

  public void addChatRoomId(String sessionId, String roomId) {
    ChatUser chatUser = findBySessionId(sessionId);
    Set<String> chatRoomIds = chatUser.getChatRoomIds();
    chatRoomIds.add(roomId);
    chatUserRepository.updateChatRoomIds(sessionId, chatRoomIds);
  }

  public void removeChatRoomId(String sessionId, String roomId) {
    ChatUser chatUser = findBySessionId(sessionId);
    Set<String> chatRoomIds = chatUser.getChatRoomIds();
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
}
