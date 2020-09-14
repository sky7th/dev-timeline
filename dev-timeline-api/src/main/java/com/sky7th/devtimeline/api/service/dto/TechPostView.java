package com.sky7th.devtimeline.api.service.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TechPostView {

    private final List<TechPostItem> techPosts;
    private final long techPostCounts;
    private List<TechPostDto> techPostDtos = new ArrayList<>();

    public TechPostView(List<TechPostItem> techPosts, long techPostCounts) {
        this.techPosts = techPosts;
        this.techPostCounts = techPostCounts;
        this.map();
    }

    private void map() {
        for (TechPostItem techPost : techPosts) {
            techPostDtos.add(new TechPostDto(techPost));
        }
    }
    
}
