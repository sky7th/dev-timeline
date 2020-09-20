package com.sky7th.devtimeline.batch.dto;

import com.sky7th.devtimeline.batch.utils.DateTimeCustomParser;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrl;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrlType;
import com.sky7th.devtimeline.core.domain.recruitpost.domain.RecruitPost;
import com.sky7th.devtimeline.core.domain.techpost.domain.TechPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingDto {

    private String crawlId;
    private CompanyUrl companyUrl;
    private String title;
    private String description;
    private String author;
    private String closingDate;
    private String date;
    private String thumbnailUrl;
    private String contentUrl;

    public RecruitPost toRecruitPost() {
        return RecruitPost.builder()
                .postCrawlId(this.crawlId)
                .companyUrl(this.companyUrl)
                .contentUrl(this.contentUrl)
                .closingDate(this.closingDate)
                .title(this.title)
                .sortDate(DateTimeCustomParser.parseClosingTime(closingDate))
                .build();
    }

    public TechPost toTechPost() {
        return TechPost.builder()
                .postCrawlId(this.crawlId)
                .companyUrl(this.companyUrl)
                .contentUrl(this.contentUrl)
                .author(this.author)
                .title(this.title)
                .description(this.description)
                .date(this.date)
                .thumbnailUrl(this.thumbnailUrl)
                .sortDate(DateTimeCustomParser.parseDateTime(this.date))
                .build();
    }

    public boolean isCompanyUrlType(CompanyUrlType companyUrlType) {
        return this.companyUrl.getCompanyUrlType().equals(companyUrlType);
    }



    public String toString() {
        return companyUrl.getCompany().getCompanyType().getName()
                +","+companyUrl.getCompanyUrlType().getName()
                +","+title
                +","+(date==null ? "" : date)+(closingDate==null ? "" : closingDate)
                +","+contentUrl;
    }
}
