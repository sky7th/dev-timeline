package com.sky7th.devtimeline.core.domain.techpost;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class TechPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="company_url_id", foreignKey = @ForeignKey(name = "fk_tech_post_company_url"))
    private CompanyUrl companyUrl;

    private String title;

    private String author;

    private String date;

    private String thumbnailUrl;

    private String contentUrl;

    private LocalDateTime sortDate;

    @Builder
    public TechPost(String title, String author, String date, String thumbnailUrl, String contentUrl, LocalDateTime sortDate) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.thumbnailUrl = thumbnailUrl;
        this.contentUrl = contentUrl;
        this.sortDate = sortDate;
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