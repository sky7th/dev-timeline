package com.sky7th.devtimeline.chat.service;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import com.sky7th.devtimeline.chat.repository.ChatMessageRepository;
import com.sky7th.devtimeline.chat.service.dto.ChatMessageRequestDto;
import com.sky7th.devtimeline.chat.service.dto.ChatMessageResponseDto;
import com.sky7th.devtimeline.chat.service.event.OnGenerateSaveMessagesEvent;
import com.sky7th.devtimeline.core.domain.chattingMessage.domain.ChattingMessage;
import com.sky7th.devtimeline.core.domain.chattingMessage.dto.ChattingMessageResponseDto;
import com.sky7th.devtimeline.core.domain.chattingMessage.dto.ChattingMessageResponseDtos;
import com.sky7th.devtimeline.core.domain.chattingMessage.service.ChattingMessageInternalService;
import com.sky7th.devtimeline.core.utils.LocalDateTimeUtils;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;
  private final ChatSessionService chatSessionService;
  private final ChattingMessageInternalService chattingMessageInternalService;

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
    onSaveMessagesEvent(new OnGenerateSaveMessagesEvent(chatMessages));
  }

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
        .forEach(chatMessage -> chatMessageRepository.deleteById(chatMessage.getId()));
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
