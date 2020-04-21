package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LinkPostViewItems {

    private final List<LinkPostDto> linkPosts;
    private final long linkPostCounts;
    private List<LinkPostViewItem> linkPostItems = new ArrayList<>();

    public LinkPostViewItems(List<LinkPostDto> linkPosts, long linkPostCounts) {
        this.linkPosts = linkPosts;
        this.linkPostCounts = linkPostCounts;
        this.map();
    }

    private void map() {
        for (LinkPostDto linkPost : linkPosts) {
            linkPostItems.add(new LinkPostViewItem(linkPost));
        }
    }
    
}
