package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.linkpost.LinkPost;
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
public class LinkPostViewItem {

    private Long id;
    private String title;
    private String content;
    private List<TagItem> tags;
    private String linkUrl;
    private String createdDate;

    public LinkPostViewItem(LinkPost linkPost) {
        this.id = linkPost.getId();
        this.title = linkPost.getTitle();
        this.content = linkPost.getContent();
        this.tags = linkPost.getTags().stream().map(TagItem::new).collect(Collectors.toList());
        this.linkUrl = linkPost.getLinkUrl();
        this.createdDate = toStringDate(linkPost.getCreatedDate(), "yyyy-MM-dd");
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