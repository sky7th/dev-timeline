package com.sky7th.devtimeline.chat.service;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnGeneratePushMessageEvent extends ApplicationEvent {

  private ChatMessage chatMessage;

  public OnGeneratePushMessageEvent(ChatMessage chatMessage) {
    super(chatMessage);
    this.chatMessage = chatMessage;
  }
}
