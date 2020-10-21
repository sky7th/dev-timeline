package com.sky7th.devtimeline.chat.inMemory.chatMessage.event;

import com.sky7th.devtimeline.chat.inMemory.chatPubsub.service.ChatPubSubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OnGeneratePushMessageEventListener {

  private final ChatPubSubService chatPubSubService;

  @Async
  @EventListener
  public void onPublishEvent(OnGeneratePushMessageEvent onGenerateEmailVerificationEvent) {
    chatPubSubService.publish(onGenerateEmailVerificationEvent.getChatMessage());
  }
}
