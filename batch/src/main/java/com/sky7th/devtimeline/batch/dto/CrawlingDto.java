package com.sky7th.devtimeline.batch.dto;

import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;
import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;
import com.sky7th.devtimeline.core.domain.techpost.TechPost;
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

    private String date;

    private String thumbnailUrl;

    private String contentUrl;

    public String toString() {
        return companyUrl.getCompany().getCompanyType().getName()+","+title+","+closingDate+","+contentUrl;
    }

    public RecruitPost toRecruitPost() {
        RecruitPost recruitPost = RecruitPost.builder()
                .contentUrl(this.contentUrl)
                .closingDate(this.closingDate)
                .title(this.title)
                .build();
        recruitPost.setCompanyUrl(this.companyUrl);

        return recruitPost;
    }

    public TechPost toTechPost() {
        TechPost techPost = TechPost.builder()
                .contentUrl(this.contentUrl)
                .author(this.author)
                .title(this.title)
                .thumbnailUrl(this.thumbnailUrl)
                .build();
        techPost.setCompanyUrl(this.companyUrl);

        return techPost;
    }

    public boolean isCompanyUrlType(CompanyUrlType companyUrlType) {
        return this.companyUrl.getCompanyUrlType().equals(companyUrlType);
    }

}
