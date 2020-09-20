package com.sky7th.devtimeline.core.domain.techpost.dto;

import com.sky7th.devtimeline.core.domain.techpost.domain.TechPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TechPostItem {

    private TechPost techPost;
    private Long postId;
    private Long likeCount;
    private Boolean isLike;
}
