package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.comment.Comment;
import com.sky7th.devtimeline.core.domain.post.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.sky7th.devtimeline.core.domain.utils.LocalDateTimeUtils.toStringDate;

@RequiredArgsConstructor
@Getter
public class CommentDto {
    private Long id;
    private Long postId;
    private UserDto user;
    private String content;
    private String createdDate;

    public CommentDto(Comment entity) {
        this.id = entity.getId();
        this.postId = entity.getPost().getId();
        this.user = new UserDto(entity.getUser());
        this.content = entity.getContent();
        this.createdDate = toStringDate(entity.getCreatedDate(), "yyyy-MM-dd HH:mm:ss");
    }

    public Comment toEntity() {
        return Comment.builder()
                .post(Post.builder()
                        .id(this.postId)
                        .build())
                .content(this.content)
                .build();
    }
}
