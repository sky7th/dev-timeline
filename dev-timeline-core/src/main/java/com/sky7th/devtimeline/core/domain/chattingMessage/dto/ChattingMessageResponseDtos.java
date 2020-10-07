package com.sky7th.devtimeline.core.domain.chattingMessage.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class ChattingMessageResponseDtos implements Serializable {

    public static final int INDEX_PARAMETER = 1;

    private int pageNumber;
    private List<ChattingMessageResponseDto> messages;

    public ChattingMessageResponseDtos(int pageNumber, List<ChattingMessageResponseDto> messages) {
        this.pageNumber = pageNumber + INDEX_PARAMETER;
        this.messages = messages;
    }
}
