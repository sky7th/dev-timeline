package com.sky7th.devtimeline.crawlpost.techpost.domain;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.crawlpost.company.domain.Company;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrl;
import java.time.LocalDateTime;
import java.util.Objects;
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

    public void update(String title, String date, String thumbnailUrl) {
        this.title = title;
        this.date = date;
        this.thumbnailUrl = thumbnailUrl;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TechPost techPost = (TechPost) o;
        return Objects.equals(postCrawlId, techPost.postCrawlId) &&
            Objects.equals(title, techPost.title) &&
            Objects.equals(description, techPost.description) &&
            Objects.equals(author, techPost.author) &&
            Objects.equals(date, techPost.date) &&
            Objects.equals(thumbnailUrl, techPost.thumbnailUrl) &&
            Objects.equals(contentUrl, techPost.contentUrl);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(postCrawlId, title, description, author, date, thumbnailUrl, contentUrl);
    }
}