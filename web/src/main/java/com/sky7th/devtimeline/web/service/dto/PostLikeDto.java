package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.like.PostLike;
import com.sky7th.devtimeline.core.domain.post.Post;
import com.sky7th.devtimeline.core.domain.post.PostType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostLikeDto {

    private PostType postType;
    private Long postId;
    private UserDto user;

    public PostLikeDto(PostType postType, Long postId, UserDto user) {
        this.postType = postType;
        this.postId = postId;
        this.user = user;
    }

    public PostLike toLike() {
        return PostLike.builder()
                .postType(this.postType)
                .post(Post.builder()
                        .id(this.postId)
                        .build())
                .build();
    }

}
