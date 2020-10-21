package com.sky7th.devtimeline.chat.inMemory.chatMessage.service;

import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessage;
import com.sky7th.devtimeline.chat.domain.chattingMessage.dto.ChattingMessageResponseDto;
import com.sky7th.devtimeline.chat.domain.chattingMessage.dto.ChattingMessageResponseDtos;
import com.sky7th.devtimeline.chat.domain.chattingMessage.service.ChattingMessageInternalService;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.domain.ChatMessage;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.domain.ChatMessageRepository;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.dto.ChatMessageRequestDto;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.dto.ChatMessageResponseDto;
import com.sky7th.devtimeline.chat.inMemory.chatSession.service.ChatSessionService;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.event.OnGenerateSaveMessagesEvent;
import com.sky7th.devtimeline.core.utils.LocalDateTimeUtils;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;
  private final ChatSessionService chatSessionService;
  private final ChattingMessageInternalService chattingMessageInternalService;
  private final ApplicationEventPublisher applicationEventPublisher;

  public List<ChatMessageResponseDto> findByRoomId(Long roomId) {
    List<ChatMessage> chatMessages = chatMessageRepository.findAllByRoomId(roomId).stream()
        .sorted(Comparator.comparing((ChatMessage chatMessage) ->
            LocalDateTimeUtils.toLocalDateTimeForMilisecond(chatMessage.getCreatedDate())))
        .collect(Collectors.toList());

    return ChatMessageResponseDto.of(chatMessages);
  }

  public ChattingMessageResponseDtos findByRoomId(Long roomId, Pageable pageable, Long start) {
    Page<ChattingMessage> pages = chattingMessageInternalService.findByRoomId(roomId, pageable, start);
    List<ChattingMessageResponseDto> chattingMessageResponseDtos = pages.stream()
        .map(ChattingMessageResponseDto::of)
        .collect(Collectors.toList());

    return new ChattingMessageResponseDtos(pageable.getPageNumber(), chattingMessageResponseDtos);
  }

  public ChatMessage save(ChatMessageRequestDto requestDto) {
    int userCount = chatSessionService.countByRoomId(requestDto.getRoomId());
    ChatMessage chatMessage = ChatMessageRequestDto.toTalkMessageEntity(requestDto, userCount);

    return save(chatMessage);
  }

  public ChatMessage save(ChatMessage chatMessage) {
    ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
    MessageCounter.count();

    if (MessageCounter.isOverLimit()) {
      savePersistentlyAllMessageInMemory();
    }

    return savedChatMessage;
  }

  public void savePersistentlyAllMessageInMemory() {
    List<ChatMessage> chatMessages = chatMessageRepository.findAll();
    applicationEventPublisher.publishEvent(new OnGenerateSaveMessagesEvent(chatMessages));
  }

  public void deleteById(String chatMessageId) {
    chatMessageRepository.deleteById(chatMessageId);
  }

  public static class MessageCounter {

    private static final int MESSAGE_LIMIT = 100;

    private static int messageCount = 0;

    public synchronized static void count() {
      MessageCounter.messageCount += 1;
    }

    public synchronized static void decreaseMessageCount(int amount) {
      MessageCounter.messageCount -= amount;
    }

    public static boolean isOverLimit() {
      return MessageCounter.messageCount >= MESSAGE_LIMIT;
    }
  }
}
