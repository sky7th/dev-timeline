package com.sky7th.devtimeline.api.service.dto;

import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitPostItem {

    private RecruitPost recruitPost;
    private Long postId;
    private Long likeCount;
    private Boolean isLike;
}
