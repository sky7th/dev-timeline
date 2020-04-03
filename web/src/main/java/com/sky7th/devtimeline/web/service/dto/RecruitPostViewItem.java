package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;
import lombok.Getter;

import static com.sky7th.devtimeline.core.domain.utils.LocalDateTimeUtils.toStringDate;

@Getter
public class RecruitPostViewItem {

    private Long id;
    private String companyTypeName;
    private String companyUrlTypeName;
    private String title;
    private String closingDate;
    private String contentUrl;
    private String sortDate;

    public RecruitPostViewItem(RecruitPost recruitPost) {
        this.id = recruitPost.getId();
        this.companyTypeName = recruitPost.getCompanyUrl().getCompany().getCompanyType().getName();
        this.companyUrlTypeName = recruitPost.getCompanyUrl().getCompanyUrlType().getName();
        this.title = recruitPost.getTitle();
        this.closingDate = recruitPost.getClosingDate();
        this.contentUrl = recruitPost.getContentUrl();
        this.sortDate = toStringDate(recruitPost.getSortDate(), "yyyy-MM-dd");
    }
}
