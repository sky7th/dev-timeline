package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.like.PostLike;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkPostDto {

    private LinkPost linkPost;
    private Long likeCount;
    private PostLike isLike;

}
