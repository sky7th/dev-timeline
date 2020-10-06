package com.sky7th.devtimeline.core.domain.chattingMessage.service;

import com.sky7th.devtimeline.core.domain.chattingMessage.domain.ChattingMessage;
import com.sky7th.devtimeline.core.domain.chattingMessage.domain.ChattingMessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChattingMessageInternalService {

    private final ChattingMessageRepository chattingMessageRepository;

    @Transactional(readOnly = true)
    public List<ChattingMessage> findAll() {
        return chattingMessageRepository.findAll();
    }

    public void saveAll(List<ChattingMessage> chattingMessages) {
        chattingMessageRepository.saveAll(chattingMessages);
    }
}
