package com.sky7th.devtimeline.chat.controller;

import com.sky7th.devtimeline.chat.service.ChatPubSubService;
import com.sky7th.devtimeline.chat.service.ChatRoomService;
import com.sky7th.devtimeline.chat.service.dto.ChatRoomResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatRoomController {

  private final ChatPubSubService chatPubSubService;
  private final ChatRoomService chatRoomService;

  @GetMapping("/rooms")
  public ResponseEntity<List<ChatRoomResponseDto>> findAllRoom() {
    return ResponseEntity.ok(chatRoomService.findAll());
  }

  @DeleteMapping("/rooms/{roomId}")
  public void deleteRoom(@PathVariable String roomId) {
    chatPubSubService.deleteRoom(roomId);
  }
}