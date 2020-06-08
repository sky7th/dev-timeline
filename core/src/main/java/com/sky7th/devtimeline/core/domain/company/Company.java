package com.sky7th.devtimeline.core.domain.company;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private CompanyType companyType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<CompanyUrl> companyUrls = new ArrayList<>();

    private String logoUrl;

    @Builder
    public Company(CompanyType companyType) {
        this.companyType = companyType;
    }
}