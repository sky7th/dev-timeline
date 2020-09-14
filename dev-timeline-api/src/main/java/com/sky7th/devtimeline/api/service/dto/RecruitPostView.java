package com.sky7th.devtimeline.api.service.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RecruitPostView {

    private final List<RecruitPostItem> recruitPosts;
    private final long recruitPostCounts;
    private List<RecruitPostDto> recruitPostDtos = new ArrayList<>();

    public RecruitPostView(List<RecruitPostItem> recruitPosts, long recruitPostCounts) {
        this.recruitPosts = recruitPosts;
        this.recruitPostCounts = recruitPostCounts;
        this.map();
    }

    private void map() {
        for (RecruitPostItem recruitPost : recruitPosts) {
            recruitPostDtos.add(new RecruitPostDto(recruitPost));
        }
    }
}
