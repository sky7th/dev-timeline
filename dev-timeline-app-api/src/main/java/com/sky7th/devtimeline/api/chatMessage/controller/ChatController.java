package com.sky7th.devtimeline.api.chatMessage.controller;

import com.sky7th.devtimeline.api.chatMessage.service.ChatService;
import com.sky7th.devtimeline.chat.domain.chattingMessage.dto.ChattingMessageResponseDtos;
import com.sky7th.devtimeline.chat.domain.chattingMessage.service.ChattingMessageInternalService;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.dto.ChatMessageRequestDto;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.dto.ChatMessageResponseDto;
import com.sky7th.devtimeline.user.dto.UserContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

  private final ChatService chatService;

  @MessageMapping("/chat/rooms/messages")
  public void pushMessage(ChatMessageRequestDto requestDto) {
    chatService.pushMessage(requestDto);
  }

  @GetMapping("/chat/rooms/{roomId}/messages/first")
  public ResponseEntity<List<ChatMessageResponseDto>> firstList(@PathVariable Long roomId) {
    return ResponseEntity.ok(chatService.findByRoomId(roomId));
  }

  @GetMapping("/chat/rooms/{roomId}/messages")
  public ResponseEntity<ChattingMessageResponseDtos> list(@PathVariable Long roomId,
      @PageableDefault(
          size = ChattingMessageInternalService.DEFAULT_MESSAGE_PAGE_SIZE,
          sort = "id",
          direction = Direction.DESC) Pageable pageable,
      @RequestParam(value = "start") Long start) {
    return ResponseEntity.ok(chatService.findByRoomId(roomId, pageable, start));
  }

  @GetMapping("/chat/messages/savePersistently")
  public ResponseEntity<Void> savePersistently(UserContext userContext) {
    chatService.savePersistentlyAllMessageInMemory(userContext);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/health")
  public ResponseEntity<Void> checkHealth() {
    return ResponseEntity.ok().build();
  }
}