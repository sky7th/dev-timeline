package com.sky7th.devtimeline.chat.service.dto;

import com.sky7th.devtimeline.chat.model.ChatRoom;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChatRoomResponseDto implements Serializable {

    private String id;
    private Long roomId;
    private String name;
    private int userCount;

    public static ChatRoomResponseDto of(ChatRoom entity) {
        return new ChatRoomResponseDto(entity.getId(), entity.getRoomId(), entity.getName(), entity.getUserCount());
    }
}
