package com.sky7th.devtimeline.chat.controller;

import com.sky7th.devtimeline.chat.service.ChatMessageService;
import com.sky7th.devtimeline.chat.service.ChatPubSubService;
import com.sky7th.devtimeline.chat.service.dto.ChatMessageRequestDto;
import com.sky7th.devtimeline.chat.service.dto.ChatMessageResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

  private final ChatPubSubService chatPubSubService;
  private final ChatMessageService chatMessageService;

  @MessageMapping("/chat/rooms/message")
  public void pushMessage(ChatMessageRequestDto requestDto) {
    chatPubSubService.publish(chatMessageService.save(requestDto));
  }

  @GetMapping("/chat/rooms/{roomId}/message")
  public ResponseEntity<List<ChatMessageResponseDto>> list(@PathVariable String roomId) {
    return ResponseEntity.ok(chatMessageService.findFirst30ByRoomId(roomId));
  }
}