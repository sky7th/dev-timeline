package com.sky7th.devtimeline.core.domain.company;

import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private CompanyType companyType;

    @Builder
    public Company(CompanyType companyType) {
        this.companyType = companyType;
    }
}