package com.sky7th.devtimeline.core.domain.company;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "COMPANY")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Company {

    @Id
    @GeneratedValue
    @Column(name = "COMPANY_ID")
    private Long id;

    @Column(name = "NAME")
    private CompanyType companyType;

}