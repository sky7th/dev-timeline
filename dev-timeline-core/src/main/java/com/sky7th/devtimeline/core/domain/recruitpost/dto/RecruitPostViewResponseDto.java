package com.sky7th.devtimeline.core.domain.recruitpost.dto;

import static com.sky7th.devtimeline.core.utils.LocalDateTimeUtils.toStringDate;

import com.sky7th.devtimeline.core.domain.company.dto.CompanyResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class RecruitPostViewResponseDto {

    private Long id;
    private CompanyResponseDto company;
    private String title;
    private String closingDate;
    private String contentUrl;
    private String sortDate;
    private Long likeCount;
    private boolean isLike;

    public static RecruitPostViewResponseDto of(RecruitPostItem recruitPostItem) {
        return RecruitPostViewResponseDto.builder()
            .id(recruitPostItem.getPostId())
            .company(CompanyResponseDto.of(recruitPostItem.getRecruitPost().getCompany()))
            .title(recruitPostItem.getRecruitPost().getTitle())
            .closingDate(recruitPostItem.getRecruitPost().getClosingDate())
            .contentUrl(recruitPostItem.getRecruitPost().getContentUrl())
            .sortDate(toStringDate(recruitPostItem.getRecruitPost().getSortDate(), "yyyy-MM-dd"))
            .likeCount(recruitPostItem.getLikeCount())
            .isLike(recruitPostItem.getIsLike())
            .build();
    }

    public static List<RecruitPostViewResponseDto> of(List<RecruitPostItem> recruitPostItems) {
        return recruitPostItems.stream().map(RecruitPostViewResponseDto::of).collect(Collectors.toList());
    }
}
