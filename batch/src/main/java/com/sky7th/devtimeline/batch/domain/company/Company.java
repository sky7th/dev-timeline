package com.sky7th.devtimeline.batch.domain.company;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "company")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Company {

    @Id
    @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    private String name;

    private String url;

}