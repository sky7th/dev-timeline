package com.sky7th.devtimeline.generalpost.linkpost.dto;

import com.sky7th.devtimeline.generalpost.linkpost.domain.LinkPost;
import com.sky7th.devtimeline.post.post.domain.Post;
import com.sky7th.devtimeline.post.post.domain.PostType;
import com.sky7th.devtimeline.post.tag.dto.TagRequestDto;
import com.sky7th.devtimeline.user.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LinkPostRequestDto {

    private Long id;
    private String title;
    private String content;
    private List<TagRequestDto> tags;
    private String linkUrl;

    public static LinkPost toEntity(LinkPostRequestDto requestDto, User user) {
        return LinkPost.builder()
            .post(new Post(PostType.LINK_POST, TagRequestDto.toEntities(requestDto.tags, user.getId())))
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .linkUrl(requestDto.getLinkUrl())
            .user(user)
            .build();
    }
}