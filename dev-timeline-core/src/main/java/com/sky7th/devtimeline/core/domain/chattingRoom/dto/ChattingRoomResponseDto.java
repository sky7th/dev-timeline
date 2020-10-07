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
    private String imageUrl;
    private String name;
    private int userCount;

    public static ChattingRoomResponseDto of(ChattingRoom entity, int userCount) {
        return ChattingRoomResponseDto.builder()
            .id(entity.getId())
            .imageUrl(entity.getImageUrl())
            .name(entity.getName())
            .userCount(userCount)
            .build();
    }

    public static List<ChattingRoomResponseDto> of(List<ChattingRoom> entities, int userCount) {
        return entities.stream()
            .map(entity -> ChattingRoomResponseDto.of(entity, userCount))
            .collect(Collectors.toList());
    }
}
