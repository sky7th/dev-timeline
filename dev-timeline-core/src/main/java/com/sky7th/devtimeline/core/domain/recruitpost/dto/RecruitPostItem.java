package com.sky7th.devtimeline.core.domain.recruitpost.dto;

import com.sky7th.devtimeline.core.domain.recruitpost.domain.RecruitPost;
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
