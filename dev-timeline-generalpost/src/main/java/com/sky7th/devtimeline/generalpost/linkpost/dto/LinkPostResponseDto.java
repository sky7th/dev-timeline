package com.sky7th.devtimeline.generalpost.linkpost.dto;

import static com.sky7th.devtimeline.core.utils.LocalDateTimeUtils.toStringDate;

import com.sky7th.devtimeline.generalpost.linkpost.domain.LinkPost;
import com.sky7th.devtimeline.post.tag.dto.TagResponseDto;
import com.sky7th.devtimeline.user.dto.UserResponseDto;
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
public class LinkPostResponseDto {

    private Long id;
    private UserResponseDto user;
    private String title;
    private String content;
    private List<TagResponseDto> tags;
    private String linkUrl;
    private String createdDate;

    public static LinkPostResponseDto of(LinkPost entity) {
        return LinkPostResponseDto.builder()
            .id(entity.getPostId())
            .user(UserResponseDto.of(entity.getUser()))
            .title(entity.getTitle())
            .content(entity.getContent())
            .tags(TagResponseDto.of(entity.getPost().getTags()))
            .linkUrl(entity.getLinkUrl())
            .createdDate(toStringDate(entity.getCreatedDate(), "yyyy-MM-dd HH:mm:ss"))
            .build();
    }

    public static List<LinkPostResponseDto> of(List<LinkPost> entities) {
        return entities.stream().map(LinkPostResponseDto::of).collect(Collectors.toList());
    }
}