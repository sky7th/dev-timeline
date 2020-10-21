package com.sky7th.devtimeline.api.chatMessage.service;

import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessage.MessageType;
import com.sky7th.devtimeline.chat.domain.chattingMessage.dto.ChattingMessageResponseDtos;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.dto.ChatMessageRequestDto;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.dto.ChatMessageResponseDto;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.service.ChatMessageService;
import com.sky7th.devtimeline.chat.inMemory.chatPubsub.service.ChatPubSubService;
import com.sky7th.devtimeline.user.dto.UserContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatPubSubService chatPubSubService;
    private final ChatMessageService chatMessageService;

    public void pushMessage(ChatMessageRequestDto requestDto) {
        if (requestDto.getType() != MessageType.ENTER) {
            chatPubSubService.publish(chatMessageService.save(requestDto));
        }
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> findByRoomId(Long roomId) {
        return chatMessageService.findByRoomId(roomId);
    }

    @Transactional(readOnly = true)
    public ChattingMessageResponseDtos findByRoomId(Long roomId, Pageable pageable, Long start) {
        return chatMessageService.findByRoomId(roomId, pageable, start);
    }

    @PreAuthorize("#userContext.isAdmin()")
    public void savePersistentlyAllMessageInMemory(UserContext userContext) {
        chatMessageService.savePersistentlyAllMessageInMemory();
    }
}
