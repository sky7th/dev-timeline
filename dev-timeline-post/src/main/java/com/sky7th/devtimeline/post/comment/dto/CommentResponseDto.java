package com.sky7th.devtimeline.post.comment.dto;

import static com.sky7th.devtimeline.core.utils.LocalDateTimeUtils.toStringDate;

import com.sky7th.devtimeline.post.comment.domain.Comment;
import com.sky7th.devtimeline.user.dto.UserResponseDto;
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
public class CommentResponseDto {

    private Long id;
    private Long postId;
    private UserResponseDto user;
    private String content;
    private String createdDate;

    public static CommentResponseDto of(Comment entity) {
        return CommentResponseDto.builder()
            .id(entity.getId())
            .postId(entity.getPost().getId())
            .user(UserResponseDto.of(entity.getUser()))
            .content(entity.getContent())
            .createdDate(toStringDate(entity.getCreatedDate(), "yyyy-MM-dd HH:mm:ss"))
            .build();
    }

    public static List<CommentResponseDto> of(List<Comment> entities) {
        return entities.stream().map(CommentResponseDto::of).collect(Collectors.toList());
    }
}
