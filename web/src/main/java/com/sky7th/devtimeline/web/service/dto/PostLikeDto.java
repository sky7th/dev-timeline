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
    private LinkPostDto linkPost;
    private UserDto user;

    public PostLikeDto(PostType postType, LinkPostDto linkPost, UserDto user) {
        this.postType = postType;
        this.linkPost = linkPost;
        this.user = user;
    }

    public PostLike toLike() {
        return PostLike.builder()
                .postType(this.postType)
                .post(Post.builder()
                        .id(this.linkPost.getId())
                        .build())
                .build();
    }

}
