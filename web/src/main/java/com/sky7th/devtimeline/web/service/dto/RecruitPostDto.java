package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import lombok.Getter;

import static com.sky7th.devtimeline.core.domain.utils.LocalDateTimeUtils.toStringDate;

@Getter
public class RecruitPostDto {

    private Long id;
    private String companyLogoUrl;
    private String companyTypeName;
    private String companyUrlTypeName;
    private String title;
    private String closingDate;
    private String contentUrl;
    private String sortDate;
    private Long likeCount;
    private boolean isLike;

    public RecruitPostDto(RecruitPostItem recruitPost) {
        this.id = recruitPost.getPostId();
        this.companyLogoUrl = recruitPost.getRecruitPost().getCompanyUrl().getCompany().getLogoUrl();
        this.companyTypeName = recruitPost.getRecruitPost().getCompanyUrl().getCompany().getCompanyType().getName();
        this.companyUrlTypeName = recruitPost.getRecruitPost().getCompanyUrl().getCompanyUrlType().getName();
        this.title = recruitPost.getRecruitPost().getTitle();
        this.closingDate = recruitPost.getRecruitPost().getClosingDate();
        this.contentUrl = recruitPost.getRecruitPost().getContentUrl();
        this.sortDate = toStringDate(recruitPost.getRecruitPost().getSortDate(), "yyyy-MM-dd");
        this.likeCount = recruitPost.getLikeCount();
        this.isLike = recruitPost.getIsLike();
    }
}
