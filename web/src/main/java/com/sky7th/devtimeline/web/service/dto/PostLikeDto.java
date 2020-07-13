package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.like.PostLike;
import com.sky7th.devtimeline.core.domain.post.Post;
import com.sky7th.devtimeline.core.domain.post.PostType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostLikeDto {

    private Long postId;

    public PostLikeDto(Long postId) {
        this.postId = postId;
    }

    public PostLike toLike() {
        return PostLike.builder()
                .post(Post.builder()
                        .id(this.postId)
                        .build())
                .build();
    }

}
