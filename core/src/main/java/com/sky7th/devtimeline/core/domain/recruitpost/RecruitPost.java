package com.sky7th.devtimeline.core.domain.recruitpost;

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
public class RecruitPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="company_url_id", foreignKey = @ForeignKey(name = "fk_recruit_post_company_url"))
    private CompanyUrl companyUrl;

    private String title;

    private String closingDate;

    private String contentUrl;

    private LocalDateTime sortDate;

    @Builder
    public RecruitPost(String title, String closingDate, String contentUrl, LocalDateTime sortDate) {
        this.title = title;
        this.closingDate = closingDate;
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
                +","+closingDate
                +","+contentUrl;
    }

    public boolean isEqual(String str) {
        return toString().equals(str);
    }

}
