package com.sky7th.devtimeline.core.domain.linkpost.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LinkPostViewResponseDtos {

    private List<LinkPostViewResponseDto> linkPosts;
    private long searchCount;

    public static LinkPostViewResponseDtos of(List<LinkPostItem> linkPostItems, long searchCount) {
        return new LinkPostViewResponseDtos(LinkPostViewResponseDto.of(linkPostItems), searchCount);
    }
}
