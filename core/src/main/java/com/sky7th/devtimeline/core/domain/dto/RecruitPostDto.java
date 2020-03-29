package com.sky7th.devtimeline.core.domain.dto;

import com.sky7th.devtimeline.core.domain.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitPostDto {

    private Company company;

    private String title;

    private String closingDate;

    private String contentUrl;

}
