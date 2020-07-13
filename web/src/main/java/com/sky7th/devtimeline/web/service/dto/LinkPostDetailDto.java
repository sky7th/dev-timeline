package com.sky7th.devtimeline.web.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import static com.sky7th.devtimeline.core.domain.utils.LocalDateTimeUtils.toStringDate;

@RequiredArgsConstructor
@Setter
@Getter
public class LinkPostDetailDto {

    private Long id;
    private UserDto user;
    private String title;
    private String content;
    private List<TagDto> tags;
    private String linkUrl;
    private String createdDate;
    private Long likeCount;
    private Long commentCount;
    private boolean isLike;
    private List<CommentDto> comments;

    public LinkPostDetailDto(LinkPostItem linkPostItem) {
        this.id = linkPostItem.getLinkPost().getPost().getId();
        this.user = new UserDto(linkPostItem.getLinkPost().getUser());
        this.title = linkPostItem.getLinkPost().getTitle();
        this.content = linkPostItem.getLinkPost().getContent();
        this.tags = linkPostItem.getLinkPost().getPost().getTags().stream().map(TagDto::new).collect(Collectors.toList());
        this.linkUrl = linkPostItem.getLinkPost().getLinkUrl();
        this.createdDate = toStringDate(linkPostItem.getLinkPost().getCreatedDate(), "yyyy-MM-dd HH:mm");
        this.likeCount = linkPostItem.getLikeCount();
        this.isLike = linkPostItem.getIsLike();
        this.commentCount = linkPostItem.getCommentCount();
        this.comments = linkPostItem.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
    }
}