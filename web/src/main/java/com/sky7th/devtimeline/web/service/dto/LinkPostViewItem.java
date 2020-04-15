package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.linkpost.LinkPost;
import lombok.Getter;

import static com.sky7th.devtimeline.core.domain.utils.LocalDateTimeUtils.toStringDate;

@Getter
public class LinkPostViewItem {

    private Long id;
    private String title;
    private String content;
    private UserItem user;
    private String linkUrl;
    private String createdDate;

    public LinkPostViewItem(LinkPost linkPost) {
        this.id = linkPost.getId();
        this.title = linkPost.getTitle();
        this.content = linkPost.getContent();
        this.user = new UserItem(linkPost.getUser());
        this.linkUrl = linkPost.getLinkUrl();
        this.createdDate = toStringDate(linkPost.getCreatedDate(), "yyyy-MM-dd");
    }

}