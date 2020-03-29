package com.sky7th.devtimeline.core.domain.companyUrl;

import com.sky7th.devtimeline.core.domain.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "COMPANY_URL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CompanyUrl {

    @Id
    @GeneratedValue
    @Column(name = "COMPANY_URL_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="COMPANY_ID")
    private Company company;

    private CompanyUrlType companyUrlType;

    private String url;

}
