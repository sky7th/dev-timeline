package com.sky7th.devtimeline.core.domain.chattingRoom.dto;

import com.sky7th.devtimeline.core.domain.chattingRoom.domain.ChattingRoom;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class ChattingRoomResponseDto {

    private Long id;
    private String name;

    public static ChattingRoomResponseDto of(ChattingRoom entity) {
        return ChattingRoomResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    public static List<ChattingRoomResponseDto> of(List<ChattingRoom> entities) {
        return entities.stream().map(ChattingRoomResponseDto::of).collect(Collectors.toList());
    }
}
