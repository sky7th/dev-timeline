package com.sky7th.devtimeline.core.domain.comment.dto;

import com.sky7th.devtimeline.core.domain.comment.domain.Comment;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CommentResponseDtos {

    private List<CommentResponseDto> comments;

    public static CommentResponseDtos of(List<Comment> entities) {
        return new CommentResponseDtos(CommentResponseDto.of(entities));
    }
}
