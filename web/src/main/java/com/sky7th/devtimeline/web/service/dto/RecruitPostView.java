package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RecruitPostView {

    private final List<RecruitPost> recruitPosts;
    private final long recruitPostCounts;
    private List<RecruitPostDto> recruitPostDtos = new ArrayList<>();

    public RecruitPostView(List<RecruitPost> recruitPosts, long recruitPostCounts) {
        this.recruitPosts = recruitPosts;
        this.recruitPostCounts = recruitPostCounts;
        this.map();
    }

    private void map() {
        for (RecruitPost recruitPost : recruitPosts) {
            recruitPostDtos.add(new RecruitPostDto(recruitPost));
        }
    }
}
