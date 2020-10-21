package com.sky7th.devtimeline.post.post.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class PostPageResponseDto<T> {

    private List<T> posts;
    private Long searchCount;
    private Long offset;

    public PostPageResponseDto(List<T> posts, Long searchCount, Long beforeOffset) {
        this.posts = posts;
        this.searchCount = searchCount;
        this.offset = beforeOffset + posts.size();
    }
}
