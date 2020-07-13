package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.post.techpost.TechPost;
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

    public TechPostDto(TechPostItem techPost) {
        this.id = techPost.getTechPost().getId();
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
    }
}