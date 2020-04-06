package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.techpost.TechPost;
import lombok.Getter;

import static com.sky7th.devtimeline.core.domain.utils.LocalDateTimeUtils.toStringDate;

@Getter
public class TechPostViewItem {

    private Long id;
    private String companyLogoUrl;
    private String companyTypeName;
    private String companyUrlTypeName;
    private String title;
    private String author;
    private String date;
    private String thumbnailUrl;
    private String contentUrl;
    private String sortDate;

    public TechPostViewItem(TechPost techPost) {
        this.id = techPost.getId();
        this.companyLogoUrl = techPost.getCompanyUrl().getCompany().getLogoUrl();
        this.companyTypeName = techPost.getCompanyUrl().getCompany().getCompanyType().getName();
        this.companyUrlTypeName = techPost.getCompanyUrl().getCompanyUrlType().getName();
        this.title = techPost.getTitle();
        this.author = techPost.getAuthor();
        this.date = techPost.getDate();
        this.thumbnailUrl = techPost.getThumbnailUrl();
        this.contentUrl = techPost.getContentUrl();
        this.sortDate = toStringDate(techPost.getSortDate(), "yyyy-MM-dd");
    }
}