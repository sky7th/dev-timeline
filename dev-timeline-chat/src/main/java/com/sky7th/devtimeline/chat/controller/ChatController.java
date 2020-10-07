package com.sky7th.devtimeline.chat.controller;

import com.sky7th.devtimeline.chat.service.ChatMessageService;
import com.sky7th.devtimeline.chat.service.ChatPubSubService;
import com.sky7th.devtimeline.chat.service.dto.ChatMessageRequestDto;
import com.sky7th.devtimeline.chat.service.dto.ChatMessageResponseDto;
import com.sky7th.devtimeline.core.domain.chattingMessage.dto.ChattingMessageResponseDtos;
import com.sky7th.devtimeline.core.domain.chattingMessage.service.ChattingMessageInternalService;
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

  private final ChatPubSubService chatPubSubService;
  private final ChatMessageService chatMessageService;

  @MessageMapping("/chat/rooms/messages")
  public void pushMessage(ChatMessageRequestDto requestDto) {
    chatPubSubService.publish(chatMessageService.save(requestDto));
  }

  @GetMapping("/chat/rooms/{roomId}/messages/first")
  public ResponseEntity<List<ChatMessageResponseDto>> firstList(@PathVariable Long roomId) {
    return ResponseEntity.ok(chatMessageService.findByRoomId(roomId));
  }

  @GetMapping("/chat/rooms/{roomId}/messages")
  public ResponseEntity<ChattingMessageResponseDtos> list(@PathVariable Long roomId,
      @PageableDefault(
          size = ChattingMessageInternalService.DEFAULT_MESSAGE_PAGE_SIZE,
          sort = "id",
          direction = Direction.DESC) Pageable pageable,
      @RequestParam(value = "start") Long start) {
    return ResponseEntity.ok(chatMessageService.findByRoomId(roomId, pageable, start));
  }
}