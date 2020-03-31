package com.sky7th.devtimeline.core.domain.companyUrl;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.company.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "company_url")
@NoArgsConstructor
@Getter
public class CompanyUrl extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_id", foreignKey = @ForeignKey(name = "fk_url_company"))
    private Company company;

    private CompanyUrlType companyUrlType;

    private String url;

    @Builder
    public CompanyUrl(CompanyUrlType companyUrlType, String url) {
        this.companyUrlType = companyUrlType;
        this.url = url;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
