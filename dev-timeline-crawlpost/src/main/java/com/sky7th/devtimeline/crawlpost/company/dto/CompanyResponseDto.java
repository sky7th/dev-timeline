package com.sky7th.devtimeline.crawlpost.company.dto;

import com.sky7th.devtimeline.crawlpost.company.domain.Company;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CompanyResponseDto {

    private String name;
    private String type;
    private String logoUrl;

    public static CompanyResponseDto of(Company entity) {
        return new CompanyResponseDto(
            entity.getCompanyType().getName(),
            entity.getCompanyType().toString(),
            entity.getLogoUrl());
    }

    public static List<CompanyResponseDto> of(List<Company> entities) {
        return entities.stream()
            .map(CompanyResponseDto::of)
            .collect(Collectors.toList());
    }
}
