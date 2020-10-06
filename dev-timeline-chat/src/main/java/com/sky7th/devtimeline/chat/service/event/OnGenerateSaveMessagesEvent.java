package com.sky7th.devtimeline.chat.service.event;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnGenerateSaveMessagesEvent extends ApplicationEvent {

  private List<ChatMessage> chatMessages;

  public OnGenerateSaveMessagesEvent(List<ChatMessage> chatMessages) {
    super(chatMessages);
    this.chatMessages = chatMessages;
  }
}
