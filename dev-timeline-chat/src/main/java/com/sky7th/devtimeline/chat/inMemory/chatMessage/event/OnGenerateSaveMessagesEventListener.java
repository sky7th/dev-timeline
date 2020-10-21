package com.sky7th.devtimeline.chat.inMemory.chatMessage.event;

import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessage;
import com.sky7th.devtimeline.chat.domain.chattingMessage.service.ChattingMessageInternalService;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.domain.ChatMessage;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.service.ChatMessageService;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.service.ChatMessageService.MessageCounter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OnGenerateSaveMessagesEventListener {

  private final ChattingMessageInternalService chattingMessageInternalService;
  private final ChatMessageService chatMessageService;

  @Async
  @EventListener
  public void onSaveMessagesEvent(OnGenerateSaveMessagesEvent onGenerateSaveMessagesEvent) {
    List<ChattingMessage> saveMessages = onGenerateSaveMessagesEvent.getChatMessages().stream()
        .map(ChatMessage::from)
        .sorted(Comparator.comparing(ChattingMessage::getCreatedDate))
        .collect(Collectors.toList());

    chattingMessageInternalService.saveAll(saveMessages);
    MessageCounter.decreaseMessageCount(saveMessages.size());
    onGenerateSaveMessagesEvent.getChatMessages()
        .forEach(chatMessage -> chatMessageService.deleteById(chatMessage.getId()));
  }
}
