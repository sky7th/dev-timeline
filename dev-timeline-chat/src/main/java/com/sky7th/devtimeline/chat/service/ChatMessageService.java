package com.sky7th.devtimeline.chat.service;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import com.sky7th.devtimeline.chat.model.ChatRoom;
import com.sky7th.devtimeline.chat.repository.ChatMessageRepository;
import com.sky7th.devtimeline.chat.service.dto.ChatMessageRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;
  private final ChatRoomService chatRoomService;

  public List<ChatMessage> findFirst30ByRoomId(String roomId) {
    return chatMessageRepository.findFirst30ByRoomId(roomId);
  }

  public ChatMessage save(ChatMessage chatMessage) {
    return chatMessageRepository.save(chatMessage);
  }

  public ChatMessage save(ChatMessageRequestDto requestDto) {
    ChatRoom chatRoom = chatRoomService.findById(requestDto.getRoomId());
    ChatMessage chatMessage = ChatMessageRequestDto.toTalkMessageEntity(requestDto, chatRoom.getUserCount());

    return chatMessageRepository.save(chatMessage);
  }
}
