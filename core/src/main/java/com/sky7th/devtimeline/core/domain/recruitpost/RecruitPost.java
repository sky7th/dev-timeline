package com.sky7th.devtimeline.core.domain.recruitpost;

import com.sky7th.devtimeline.core.domain.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class RecruitPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="company_id", foreignKey = @ForeignKey(name = "fk_recruit_post_company"))
    private Company company;

    private String title;

    private String closingDate;

    private String contentUrl;

    @Builder
    public RecruitPost(String title, String closingDate, String contentUrl) {
        this.title = title;
        this.closingDate = closingDate;
        this.contentUrl = contentUrl;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
