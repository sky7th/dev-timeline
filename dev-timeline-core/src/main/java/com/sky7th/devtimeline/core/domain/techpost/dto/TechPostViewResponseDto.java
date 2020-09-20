package com.sky7th.devtimeline.core.domain.techpost.dto;

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
public class TechPostViewResponseDto {

    private Long id;
    private CompanyResponseDto company;
    private String title;
    private String description;
    private String author;
    private String date;
    private String thumbnailUrl;
    private String contentUrl;
    private String sortDate;
    private Long likeCount;
    private boolean isLike;

    public static TechPostViewResponseDto of(TechPostItem techPostItem) {
        return TechPostViewResponseDto.builder()
            .id(techPostItem.getPostId())
            .company(CompanyResponseDto.of(techPostItem.getTechPost().getCompany()))
            .title(techPostItem.getTechPost().getTitle())
            .description(techPostItem.getTechPost().getDescription())
            .author(techPostItem.getTechPost().getAuthor())
            .date(techPostItem.getTechPost().getDate())
            .thumbnailUrl(techPostItem.getTechPost().getThumbnailUrl())
            .contentUrl(techPostItem.getTechPost().getContentUrl())
            .sortDate(toStringDate(techPostItem.getTechPost().getSortDate(), "yyyy-MM-dd"))
            .likeCount(techPostItem.getLikeCount())
            .isLike(techPostItem.getIsLike())
            .build();
    }

    public static List<TechPostViewResponseDto> of(List<TechPostItem> recruitPostItems) {
        return recruitPostItems.stream().map(TechPostViewResponseDto::of).collect(Collectors.toList());
    }
}