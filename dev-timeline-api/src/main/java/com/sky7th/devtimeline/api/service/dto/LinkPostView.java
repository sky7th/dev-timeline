package com.sky7th.devtimeline.api.service.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LinkPostView {

    private final List<LinkPostItem> linkPosts;
    private final long linkPostCounts;
    private List<LinkPostDto> linkPostDtos = new ArrayList<>();

    public LinkPostView(List<LinkPostItem> linkPosts, long linkPostCounts) {
        this.linkPosts = linkPosts;
        this.linkPostCounts = linkPostCounts;
        this.map();
    }

    private void map() {
        for (LinkPostItem linkPost : linkPosts) {
            linkPostDtos.add(new LinkPostDto(linkPost));
        }
    }
    
}
