package com.sky7th.devtimeline.core.domain.techpost.dto;

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
    private long searchCount;

    public static TechPostViewResponseDtos of(List<TechPostItem> techPostItems, long searchCount) {
        return new TechPostViewResponseDtos(TechPostViewResponseDto.of(techPostItems), searchCount);
    }
}
