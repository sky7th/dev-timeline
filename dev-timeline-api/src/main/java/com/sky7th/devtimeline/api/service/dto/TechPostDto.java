package com.sky7th.devtimeline.api.service.dto;

import lombok.Getter;

import static com.sky7th.devtimeline.core.domain.utils.LocalDateTimeUtils.toStringDate;

@Getter
public class TechPostDto {

    private Long id;
    private String companyLogoUrl;
    private String companyTypeName;
    private String companyUrlTypeName;
    private String title;
    private String description;
    private String author;
    private String date;
    private String thumbnailUrl;
    private String contentUrl;
    private String sortDate;
    private Long likeCount;
    private boolean isLike;

    public TechPostDto(TechPostItem techPost) {
        this.id = techPost.getPostId();
        this.companyLogoUrl = techPost.getTechPost().getCompanyUrl().getCompany().getLogoUrl();
        this.companyTypeName = techPost.getTechPost().getCompanyUrl().getCompany().getCompanyType().getName();
        this.companyUrlTypeName = techPost.getTechPost().getCompanyUrl().getCompanyUrlType().getName();
        this.title = techPost.getTechPost().getTitle();
        this.description = techPost.getTechPost().getDescription();
        this.author = techPost.getTechPost().getAuthor();
        this.date = techPost.getTechPost().getDate();
        this.thumbnailUrl = techPost.getTechPost().getThumbnailUrl();
        this.contentUrl = techPost.getTechPost().getContentUrl();
        this.sortDate = toStringDate(techPost.getTechPost().getSortDate(), "yyyy-MM-dd");
        this.likeCount = techPost.getLikeCount();
        this.isLike = techPost.getIsLike();
    }
}