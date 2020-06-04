package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.comment.Comment;
import com.sky7th.devtimeline.core.domain.post.PostType;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.sky7th.devtimeline.core.domain.utils.LocalDateTimeUtils.toStringDate;

@RequiredArgsConstructor
@Getter
public class CommentDto {
    private Long id;
    private PostType postType;
    private Long postId;
    private UserItem user;
    private String content;
    private String createdDate;

    public CommentDto(Comment entity) {
        this.id = entity.getId();
        this.postType = entity.getPostType();
        this.postId = entity.getLinkPost().getId();
        this.user = new UserItem(entity.getUser());
        this.content = entity.getContent();
        this.createdDate = toStringDate(entity.getCreatedDate(), "yyyy-MM-dd");
    }

    public Comment toEntity() {
        return Comment.builder()
                .postType(this.postType)
                .linkPost(LinkPost.builder()
                        .id(this.postId)
                        .build())
                .content(this.content)
                .build();
    }
}
