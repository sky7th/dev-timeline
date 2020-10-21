package com.sky7th.devtimeline.crawlpost.recruitpost.dto;

import com.sky7th.devtimeline.crawlpost.recruitpost.domain.RecruitPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitPostItem {

    private RecruitPost recruitPost;
    private Long postId;
    private Long likeCount;
    private Boolean isLike;
}
