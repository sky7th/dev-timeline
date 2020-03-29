package com.sky7th.devtimeline.core.domain.recruitpost;

import com.sky7th.devtimeline.core.domain.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RECRUIT_POST")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RecruitPost {

    @Id
    @GeneratedValue
    @Column(name = "RECRUIT_POST_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="COMPANY_ID")
    private Company company;

    private String title;

    private String closingDate;

    private String contentUrl;

}
