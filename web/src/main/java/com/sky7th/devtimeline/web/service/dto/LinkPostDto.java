package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.post.Post;
import com.sky7th.devtimeline.core.domain.post.PostType;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.core.domain.tag.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import static com.sky7th.devtimeline.core.domain.utils.LocalDateTimeUtils.toStringDate;

@RequiredArgsConstructor
@Setter
@Getter
public class LinkPostDto {

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

    public LinkPostDto(LinkPostItem linkPostItem) {
        this.id = linkPostItem.getLinkPost().getPost().getId();
        this.user = new UserDto(linkPostItem.getLinkPost().getUser());
        this.title = linkPostItem.getLinkPost().getTitle();
        this.content = linkPostItem.getLinkPost().getContent();
        this.tags = linkPostItem.getLinkPost().getPost().getTags().stream().map(TagDto::new).collect(Collectors.toList());
        this.linkUrl = linkPostItem.getLinkPost().getLinkUrl();
        this.createdDate = toStringDate(linkPostItem.getLinkPost().getCreatedDate(), "yyyy-MM-dd HH:mm:ss");
        this.likeCount = linkPostItem.getLikeCount();
        this.commentCount = linkPostItem.getCommentCount();
        this.isLike = linkPostItem.getIsLike();
    }

    public LinkPost toLinkPost() {
        LinkPost linkPost = LinkPost.builder()
                .post(Post.builder()
                    .likeCount(0)
                    .commentCount(0)
                    .postType(PostType.LINK_POST)
                    .build())
                .title(this.title)
                .content(this.content)
                .linkUrl(this.linkUrl)
                .build();
        List<Tag> tags = this.tags.stream().map(TagDto::toTag).collect(Collectors.toList());
        tags.forEach(linkPost.getPost()::addTags);

        return linkPost;
    }
}