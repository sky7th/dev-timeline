package com.sky7th.devtimeline.web.service.dto;

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
public class LinkPostViewDetailDto {

    private Long id;
    private UserItem user;
    private String title;
    private String content;
    private List<TagItem> tags;
    private String linkUrl;
    private String createdDate;
    private Long likeCount;
    private Long commentCount;
    private boolean isLike;
    private List<CommentDto> comments;

    public LinkPostViewDetailDto(LinkPostDto linkPostDto) {
        this.id = linkPostDto.getLinkPost().getId();
        this.user = new UserItem(linkPostDto.getLinkPost().getUser());
        this.title = linkPostDto.getLinkPost().getTitle();
        this.content = linkPostDto.getLinkPost().getContent();
        this.tags = linkPostDto.getLinkPost().getTags().stream().map(TagItem::new).collect(Collectors.toList());
        this.linkUrl = linkPostDto.getLinkPost().getLinkUrl();
        this.createdDate = toStringDate(linkPostDto.getLinkPost().getCreatedDate(), "yyyy-MM-dd HH:mm");
        this.likeCount = linkPostDto.getLikeCount();
        this.isLike = linkPostDto.getIsLike();
        this.commentCount = linkPostDto.getCommentCount();
        this.comments = linkPostDto.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
    }

    public LinkPost toLinkPost() {
        LinkPost linkPost = LinkPost.builder()
                .title(this.title)
                .content(this.content)
                .linkUrl(this.linkUrl)
                .build();
        List<Tag> tags = this.tags.stream().map(TagItem::toTag).collect(Collectors.toList());
        tags.forEach(linkPost::addTags);

        return linkPost;
    }

}