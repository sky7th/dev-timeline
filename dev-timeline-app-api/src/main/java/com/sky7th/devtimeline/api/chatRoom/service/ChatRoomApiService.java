package com.sky7th.devtimeline.api.chatRoom.service;

import com.sky7th.devtimeline.chat.domain.chattingRoom.dto.ChattingRoomResponseDto;
import com.sky7th.devtimeline.chat.inMemory.chatPubsub.service.ChatPubSubService;
import com.sky7th.devtimeline.chat.inMemory.chatRoom.service.ChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatRoomApiService {

    private final ChatPubSubService chatPubSubService;
    private final ChatRoomService chatRoomService;

    @Transactional(readOnly = true)
    public List<ChattingRoomResponseDto> findAll() {
        return chatRoomService.findAll();
    }

    public void init() {
        chatPubSubService.init();
    }
}
