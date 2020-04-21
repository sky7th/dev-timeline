package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.like.PostLike;
import com.sky7th.devtimeline.core.domain.post.PostType;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostLikeDto {

    private PostType postType;
    private LinkPostViewItem linkPost;
    private UserItem user;

    public PostLikeDto(PostType postType, LinkPostViewItem linkPost, UserItem user) {
        this.postType = postType;
        this.linkPost = linkPost;
        this.user = user;
    }

    public PostLike toLike() {
        return PostLike.builder()
                .postType(this.postType)
                .linkPost(LinkPost.builder()
                        .id(this.linkPost.getId())
                        .build())
                .build();
    }

}
