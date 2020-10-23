package com.sky7th.devtimeline.crawlpost.company.domain;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company_url")
@NoArgsConstructor
@Getter
public class CompanyUrl extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id", foreignKey = @ForeignKey(name = "fk_url_company"))
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(name = "url_type")
    private CompanyUrlType companyUrlType;

    private String url;

    private Integer priority;

    @Builder
    public CompanyUrl(CompanyUrlType companyUrlType, String url, Integer priority) {
        this.companyUrlType = companyUrlType;
        this.url = url;
        this.priority = priority;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
