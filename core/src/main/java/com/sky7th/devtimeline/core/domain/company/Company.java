package com.sky7th.devtimeline.core.domain.company;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import lombok.*;
import javax.persistence.*;

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

    private String logoUrl;

    @Builder
    public Company(CompanyType companyType) {
        this.companyType = companyType;
    }
}