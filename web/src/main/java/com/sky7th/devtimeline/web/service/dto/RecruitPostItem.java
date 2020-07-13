package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.comment.Comment;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitPostItem {

    private RecruitPost recruitPost;
    private Long likeCount;
    private Boolean isLike;
}
