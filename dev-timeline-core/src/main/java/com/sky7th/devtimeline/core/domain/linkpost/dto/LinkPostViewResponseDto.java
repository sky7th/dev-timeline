package com.sky7th.devtimeline.core.domain.linkpost.dto;

import static com.sky7th.devtimeline.core.utils.LocalDateTimeUtils.toStringDate;

import com.sky7th.devtimeline.core.domain.tag.dto.TagResponseDto;
import com.sky7th.devtimeline.core.domain.user.dto.UserResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class LinkPostViewResponseDto {

    private Long id;
    private UserResponseDto user;
    private String title;
    private String content;
    private List<TagResponseDto> tags;
    private String linkUrl;
    private String createdDate;
    private Long likeCount;
    private Long commentCount;
    private boolean isLike;

    public static LinkPostViewResponseDto of(LinkPostItem linkPostItem) {
        return LinkPostViewResponseDto.builder()
            .id(linkPostItem.getLinkPost().getPostId())
            .user(UserResponseDto.of(linkPostItem.getLinkPost().getUser()))
            .title(linkPostItem.getLinkPost().getTitle())
            .content(linkPostItem.getLinkPost().getContent())
            .tags(TagResponseDto.of(linkPostItem.getLinkPost().getPost().getTags()))
            .linkUrl(linkPostItem.getLinkPost().getLinkUrl())
            .createdDate(toStringDate(linkPostItem.getLinkPost().getCreatedDate(), "yyyy-MM-dd HH:mm:ss"))
            .likeCount(linkPostItem.getLinkPost().getPost().getLikeCount())
            .commentCount(linkPostItem.getLinkPost().getPost().getCommentCount())
            .isLike(linkPostItem.getIsLike())
            .build();
    }

    public static List<LinkPostViewResponseDto> of(List<LinkPostItem> linkPostItems) {
        return linkPostItems.stream().map(LinkPostViewResponseDto::of).collect(Collectors.toList());
    }
}