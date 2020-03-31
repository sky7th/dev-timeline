package com.sky7th.devtimeline.batch.dto;

import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingDto {

    private CompanyUrl companyUrl;

    private String title;

    private String author;

    private String closingDate;

    private String contentUrl;

    public String toString() {
        return companyUrl.getCompany().getCompanyType().getName()+","+title+","+closingDate+","+contentUrl;
    }

    public RecruitPost toRecruitPost(CrawlingDto crawlingDto) {
        RecruitPost recruitPost = RecruitPost.builder()
                .contentUrl(crawlingDto.getContentUrl())
                .closingDate(crawlingDto.getClosingDate())
                .title(crawlingDto.getTitle())
                .build();
        recruitPost.setCompanyUrl(crawlingDto.getCompanyUrl());

        return recruitPost;
    }

    public boolean isCompanyType(RecruitPost recruitPost) {
        return this.companyUrl.getCompany().getCompanyType().equals(recruitPost.getCompanyUrl().getCompany().getCompanyType());
    }

}
