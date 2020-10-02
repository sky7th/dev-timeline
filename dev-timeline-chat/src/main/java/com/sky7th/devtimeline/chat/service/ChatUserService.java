package com.sky7th.devtimeline.chat.service;

import com.sky7th.devtimeline.chat.model.ChatUser;
import com.sky7th.devtimeline.chat.repository.ChatUserRepository;
import com.sky7th.devtimeline.chat.service.exception.NotFoundChatUserException;
import io.jsonwebtoken.Claims;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatUserService {

  private final ChatUserRepository chatUserRepository;
  private final ChatRoomService chatRoomService;

  @Transactional(readOnly = true)
  public ChatUser findById(Long userId) {
    return chatUserRepository.findById(userId.toString()).orElseThrow(NotFoundChatUserException::new);
  }

  public void enter(String roomId, Long userId, Claims claims) {
    try {
      findById(userId);
    } catch (NotFoundChatUserException e) {
      chatUserRepository.save(getNewChatUser(claims));
    }

    addChatRoomId(userId, roomId);
    chatRoomService.addChatUserId(roomId, userId);
  }

  private ChatUser getNewChatUser(Claims claims) {
    Long userId = Long.parseLong(claims.getId());
    String userName = claims.get("name", String.class);
    String userImageUrl = claims.get("imageUrl", String.class);

    return new ChatUser(userId, userName, userImageUrl);
  }

  public void exit(String roomId, Long userId) {
    removeChatRoomId(userId, roomId);
    chatRoomService.removeChatUserId(roomId, userId);
  }

  private void addChatRoomId(Long userId, String roomId) {
    ChatUser chatUser = findById(userId);
    Set<String> chatRoomIds = chatUser.getChatRoomIds();
    chatRoomIds.add(roomId);
    chatUserRepository.updateChatRoomIds(userId, chatRoomIds);
  }

  private void removeChatRoomId(Long userId, String roomId) {
    ChatUser chatUser = findById(userId);
    Set<String> chatRoomIds = chatUser.getChatRoomIds();
    chatRoomIds.remove(roomId);
    chatUserRepository.updateChatRoomIds(userId, chatRoomIds);
  }

  public void disconnect(Long userId) {
    ChatUser chatUser = findById(userId);
    chatUser.getChatRoomIds().forEach(roomId -> {
      exit(roomId, userId);
    });
  }
}
