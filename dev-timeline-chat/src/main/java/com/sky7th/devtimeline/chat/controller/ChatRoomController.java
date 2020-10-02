package com.sky7th.devtimeline.chat.controller;

import com.sky7th.devtimeline.chat.model.ChatRoom;
import com.sky7th.devtimeline.chat.service.ChatPubSubService;
import com.sky7th.devtimeline.chat.service.ChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatRoomController {

  private final ChatPubSubService chatPubSubService;
  private final ChatRoomService chatRoomService;

  @GetMapping("/rooms")
  public List<ChatRoom> findAllRoom() {
    return chatRoomService.findAll();
  }

  @PostMapping("/rooms")
  public void createRoom(String roomName) {
    chatPubSubService.createRoom(roomName);
  }

  @DeleteMapping("/rooms/{roomId}")
  public void deleteRoom(@PathVariable String roomId) {
    chatPubSubService.deleteRoom(roomId);
  }
}