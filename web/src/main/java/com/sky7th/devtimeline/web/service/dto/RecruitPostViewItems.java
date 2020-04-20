package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RecruitPostViewItems {

    private final List<RecruitPost> recruitPosts;
    private final long recruitPostCounts;
    private List<RecruitPostViewItem> recruitPostItems = new ArrayList<>();

    public RecruitPostViewItems(List<RecruitPost> recruitPosts, long recruitPostCounts) {
        this.recruitPosts = recruitPosts;
        this.recruitPostCounts = recruitPostCounts;
        this.map();
    }

    private void map() {
        for (RecruitPost recruitPost : recruitPosts) {
            recruitPostItems.add(new RecruitPostViewItem(recruitPost));
        }
    }
}
