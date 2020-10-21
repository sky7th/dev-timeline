package com.sky7th.devtimeline.crawlpost.techpost.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class TechPostViewResponseDtos {

    private List<TechPostViewResponseDto> techPostDtos;
    private Long searchCount;

    public static TechPostViewResponseDtos of(List<TechPostItem> techPostItems, Long searchCount) {
        return new TechPostViewResponseDtos(TechPostViewResponseDto.of(techPostItems), searchCount);
    }
}
