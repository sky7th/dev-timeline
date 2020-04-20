package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.post.techpost.TechPost;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TechPostViewItems {

    private final List<TechPost> techPosts;
    private final long techPostCounts;
    private List<TechPostViewItem> techPostItems = new ArrayList<>();

    public TechPostViewItems(List<TechPost> techPosts, long techPostCounts) {
        this.techPosts = techPosts;
        this.techPostCounts = techPostCounts;
        this.map();
    }

    private void map() {
        for (TechPost techPost : techPosts) {
            techPostItems.add(new TechPostViewItem(techPost));
        }
    }
    
}
