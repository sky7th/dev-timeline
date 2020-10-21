package com.sky7th.devtimeline.chat.domain.chattingMessage.dto;

import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessage;
import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessage.MessageType;
import com.sky7th.devtimeline.core.utils.LocalDateTimeUtils;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChattingMessageResponseDto implements Serializable {

    private Long id;
    private MessageType type;
    private ChattingUserResponseDto sender;
    private String message;
    private String createdDate;

    public static ChattingMessageResponseDto of(ChattingMessage entity) {
        return new ChattingMessageResponseDto(
            entity.getId(),
            entity.getType(),
            entity.getUser() == null ? null : ChattingUserResponseDto.of(entity.getUser()),
            entity.getMessage(),
            LocalDateTimeUtils.toStringDate(entity.getCreatedDate()));
    }

    public static List<ChattingMessageResponseDto> of(List<ChattingMessage> entities) {
        return entities.stream()
            .map(ChattingMessageResponseDto::of)
            .collect(Collectors.toList());
    }
}
