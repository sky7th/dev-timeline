package com.sky7th.devtimeline.chat.inMemory.chatMessage.event;

import com.sky7th.devtimeline.chat.inMemory.chatMessage.domain.ChatMessage;
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
