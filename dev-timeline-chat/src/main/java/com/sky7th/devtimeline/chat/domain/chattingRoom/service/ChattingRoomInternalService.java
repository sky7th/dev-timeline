package com.sky7th.devtimeline.chat.domain.chattingRoom.service;

import com.sky7th.devtimeline.chat.domain.chattingRoom.domain.ChattingRoom;
import com.sky7th.devtimeline.chat.domain.chattingRoom.domain.ChattingRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChattingRoomInternalService {

    private final ChattingRoomRepository chattingRoomRepository;

    @Transactional(readOnly = true)
    public List<ChattingRoom> findAll() {
        return chattingRoomRepository.findAll();
    }
}
