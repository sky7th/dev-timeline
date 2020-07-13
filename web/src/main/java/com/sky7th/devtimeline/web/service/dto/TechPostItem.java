package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import com.sky7th.devtimeline.core.domain.post.techpost.TechPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechPostItem {

    private TechPost techPost;
    private Long postId;
    private Long likeCount;
    private Boolean isLike;
}
