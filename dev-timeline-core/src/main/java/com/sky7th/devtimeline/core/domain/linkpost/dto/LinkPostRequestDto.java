package com.sky7th.devtimeline.core.domain.linkpost.dto;

import com.sky7th.devtimeline.core.domain.linkpost.domain.LinkPost;
import com.sky7th.devtimeline.core.domain.post.domain.Post;
import com.sky7th.devtimeline.core.domain.post.domain.PostType;
import com.sky7th.devtimeline.core.domain.tag.domain.Tag;
import com.sky7th.devtimeline.core.domain.tag.dto.TagRequestDto;
import com.sky7th.devtimeline.core.domain.user.domain.User;
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
        LinkPost linkPost = LinkPost.builder()
                .post(Post.builder()
                    .likeCount(0)
                    .commentCount(0)
                    .postType(PostType.LINK_POST)
                    .build())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .linkUrl(requestDto.getLinkUrl())
                .user(user)
                .build();
        List<Tag> tags = TagRequestDto.toEntities(requestDto.tags);
        tags.forEach(linkPost.getPost()::addTag);

        return linkPost;
    }
}