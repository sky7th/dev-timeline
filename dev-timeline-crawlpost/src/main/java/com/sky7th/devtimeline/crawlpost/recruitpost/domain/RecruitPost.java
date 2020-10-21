package com.sky7th.devtimeline.crawlpost.recruitpost.domain;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.crawlpost.company.domain.Company;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrl;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RecruitPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "post_crawl_id", foreignKey = @ForeignKey(name = "fk_recruit_post_post"))
//    private Post post;
    private String postCrawlId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_url_id", foreignKey = @ForeignKey(name = "fk_recruit_post_company_url"))
    private CompanyUrl companyUrl;

    private String title;
    private String closingDate;
    private String contentUrl;
    private LocalDateTime sortDate;

    @Builder
    public RecruitPost(String postCrawlId, CompanyUrl companyUrl, String title, String closingDate, String contentUrl, LocalDateTime sortDate) {
        this.postCrawlId = postCrawlId;
        this.companyUrl = companyUrl;
        this.title = title;
        this.closingDate = closingDate;
        this.contentUrl = contentUrl;
        this.sortDate = sortDate;
    }

    public void update(String title, String closingDate) {
        this.title = title;
        this.closingDate = closingDate;
    }

    public Company getCompany() {
        return this.companyUrl.getCompany();
    }

    public void setCompanyUrl(CompanyUrl companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String toString() {
        return companyUrl.getCompany().getCompanyType().getName()
                +","+companyUrl.getCompanyUrlType().getName()
                +","+title
                +","+closingDate
                +","+contentUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecruitPost that = (RecruitPost) o;
        return Objects.equals(postCrawlId, that.postCrawlId) &&
            Objects.equals(title, that.title) &&
            Objects.equals(closingDate, that.closingDate) &&
            Objects.equals(contentUrl, that.contentUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postCrawlId, title, closingDate, contentUrl);
    }
}
