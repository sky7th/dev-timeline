package com.sky7th.devtimeline.core.domain.techpost.domain;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.company.domain.Company;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrl;
import java.time.LocalDateTime;
import javax.persistence.Column;
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
public class TechPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "post_crawl_id", foreignKey = @ForeignKey(name = "fk_tech_post_post"))
//    private Post post;
    private String postCrawlId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_url_id", foreignKey = @ForeignKey(name = "fk_tech_post_company_url"))
    private CompanyUrl companyUrl;

    private String title;
    private String description;
    private String author;
    private String date;

    @Column(length = 1000)
    private String thumbnailUrl;

    private String contentUrl;
    private LocalDateTime sortDate;

    @Builder
    public TechPost(String postCrawlId, CompanyUrl companyUrl, String title, String description, String author, String date, String thumbnailUrl, String contentUrl, LocalDateTime sortDate) {
        this.postCrawlId = postCrawlId;
        this.companyUrl = companyUrl;
        this.title = title;
        this.description = description;
        this.author = author;
        this.date = date;
        this.thumbnailUrl = thumbnailUrl;
        this.contentUrl = contentUrl;
        this.sortDate = sortDate;
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
                +","+date
                +","+contentUrl;
    }

    public boolean isEqual(String str) {
        return toString().equals(str);
    }

}