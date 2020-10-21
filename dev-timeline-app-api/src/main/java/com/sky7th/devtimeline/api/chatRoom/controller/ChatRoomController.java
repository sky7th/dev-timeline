package com.sky7th.devtimeline.api.chatRoom.controller;

import com.sky7th.devtimeline.api.chatRoom.service.ChatRoomApiService;
import com.sky7th.devtimeline.chat.domain.chattingRoom.dto.ChattingRoomResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatRoomController {

  private final ChatRoomApiService chatRoomApiService;

  @GetMapping("/rooms")
  public ResponseEntity<List<ChattingRoomResponseDto>> findAllRoom() {
    return ResponseEntity.ok(chatRoomApiService.findAll());
  }

  @GetMapping("/rooms/init")
  public ResponseEntity<Void> init() {
    chatRoomApiService.init();
    return ResponseEntity.ok().build();
  }
}