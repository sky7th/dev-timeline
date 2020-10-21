package com.sky7th.devtimeline.chat.domain.chattingMessage.service;

import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessage;
import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessageRepository;
import com.sky7th.devtimeline.chat.domain.chattingMessage.exception.InvalidPageRequstException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChattingMessageInternalService {

    public static final int DEFAULT_MESSAGE_PAGE_SIZE = 50;
    public static final int DEFAULT_START = 0;

    private final ChattingMessageRepository chattingMessageRepository;

    public void saveAll(List<ChattingMessage> chattingMessages) {
        chattingMessageRepository.saveAll(chattingMessages);
    }

    @Transactional(readOnly = true)
    public Page<ChattingMessage> findByRoomId(Long roomId, Pageable pageable, Long start) {
        if (pageable.getPageSize() != DEFAULT_MESSAGE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }

        if (start == DEFAULT_START) {
            return chattingMessageRepository.findByRoomId(roomId, pageable);
        } else {
            return chattingMessageRepository.findByRoomIdAndStart(roomId, pageable, start);
        }
    }
}
