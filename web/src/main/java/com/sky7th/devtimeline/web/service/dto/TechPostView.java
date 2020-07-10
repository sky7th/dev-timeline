package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.post.techpost.TechPost;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TechPostView {

    private final List<TechPost> techPosts;
    private final long techPostCounts;
    private List<TechPostDto> techPostDtos = new ArrayList<>();

    public TechPostView(List<TechPost> techPosts, long techPostCounts) {
        this.techPosts = techPosts;
        this.techPostCounts = techPostCounts;
        this.map();
    }

    private void map() {
        for (TechPost techPost : techPosts) {
            techPostDtos.add(new TechPostDto(techPost));
        }
    }
    
}
