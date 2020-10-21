package com.sky7th.devtimeline.crawlpost.recruitpost.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RecruitPostViewResponseDtos {

    private List<RecruitPostViewResponseDto> recruitPosts;
    private Long searchCount;

    public static RecruitPostViewResponseDtos of(List<RecruitPostItem> recruitPostItems, Long searchCount) {
        return new RecruitPostViewResponseDtos(RecruitPostViewResponseDto.of(recruitPostItems), searchCount);
    }
}
