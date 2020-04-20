package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LinkPostViewItems {

    private final List<LinkPost> linkPosts;
    private final long linkPostCounts;
    private List<LinkPostViewItem> linkPostItems = new ArrayList<>();

    public LinkPostViewItems(List<LinkPost> linkPosts, long linkPostCounts) {
        this.linkPosts = linkPosts;
        this.linkPostCounts = linkPostCounts;
        this.map();
    }

    private void map() {
        for (LinkPost linkPost : linkPosts) {
            linkPostItems.add(new LinkPostViewItem(linkPost));
        }
    }
    
}
