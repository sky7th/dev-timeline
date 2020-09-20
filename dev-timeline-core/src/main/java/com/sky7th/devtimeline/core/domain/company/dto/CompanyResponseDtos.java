package com.sky7th.devtimeline.core.domain.company.dto;

import com.sky7th.devtimeline.core.domain.company.domain.Company;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CompanyResponseDtos {

    private List<CompanyResponseDto> companies;

    public static CompanyResponseDtos of(List<Company> entities) {
        return new CompanyResponseDtos(CompanyResponseDto.of(entities));
    }
}
