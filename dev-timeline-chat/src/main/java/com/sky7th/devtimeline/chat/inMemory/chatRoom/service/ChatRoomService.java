package com.sky7th.devtimeline.chat.inMemory.chatRoom.service;

import com.sky7th.devtimeline.chat.inMemory.chatRoom.domain.ChatRoom;
import com.sky7th.devtimeline.chat.inMemory.chatSession.domain.ChatSession;
import com.sky7th.devtimeline.chat.inMemory.chatSession.service.ChatSessionService;
import com.sky7th.devtimeline.chat.inMemory.chatUser.domain.ChatUser;
import com.sky7th.devtimeline.chat.inMemory.chatRoom.domain.ChatRoomRepository;
import com.sky7th.devtimeline.chat.inMemory.chatUser.service.ChatUserService;
import com.sky7th.devtimeline.chat.inMemory.chatRoom.exception.NotFoundChatRoomException;
import com.sky7th.devtimeline.chat.domain.chattingRoom.dto.ChattingRoomResponseDto;
import com.sky7th.devtimeline.chat.domain.chattingRoom.service.ChattingRoomInternalService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;
  private final ChatUserService chatUserService;
  private final ChattingRoomInternalService chattingRoomInternalService;
  private final ChatSessionService chatSessionService;

  @Transactional(readOnly = true)
  public ChatRoom findById(String id) {
    return chatRoomRepository.findById(id).orElseThrow(NotFoundChatRoomException::new);
  }

  @Transactional(readOnly = true)
  public ChatRoom findByRoomId(Long roomId) {
    return chatRoomRepository.findByRoomId(roomId).orElseThrow(NotFoundChatRoomException::new);
  }

  public List<ChattingRoomResponseDto> findAll() {
    return chattingRoomInternalService.findAll().stream()
        .map(chattingRoom -> {
          int userCount = chatSessionService.countByRoomId(chattingRoom.getId());
          return ChattingRoomResponseDto.of(chattingRoom, userCount);
        })
        .collect(Collectors.toList());
  }

  public ChatRoom save(ChatRoom chatRoom) {
    return chatRoomRepository.save(chatRoom);
  }

  public ChatSession enter(Long roomId, ChatUser chatUser) {
    chatUserService.addChatRoomId(chatUser.getSessionId(), roomId);
    return chatSessionService.addSessionId(roomId, chatUser);
  }

  public ChatSession exit(Long roomId, ChatUser chatUser) {
    chatUserService.removeChatRoomId(chatUser.getSessionId(), roomId);
    return chatSessionService.removeSessionId(roomId, chatUser);
  }
}
