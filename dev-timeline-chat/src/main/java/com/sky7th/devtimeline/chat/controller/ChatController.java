package com.sky7th.devtimeline.chat.controller;

import com.sky7th.devtimeline.chat.service.ChatMessageService;
import com.sky7th.devtimeline.chat.service.ChatPubSubService;
import com.sky7th.devtimeline.chat.service.dto.ChatMessageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

  private final ChatPubSubService chatPubSubService;
  private final ChatMessageService chatMessageService;

  @MessageMapping("/chat/rooms/message")
  public void pushMessage(ChatMessageRequestDto requestDto) {
    chatPubSubService.pushMessage(chatMessageService.save(requestDto));
  }
}