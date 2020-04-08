package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.company.CompanyType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.sky7th.devtimeline.web.config.PagingConstant.PAGE_SIZE;

@RequiredArgsConstructor
@Getter
@Setter
public class PostSearchForm {

    Long offset = 0L;
    Long limit = PAGE_SIZE;
    List<String> tags;
    List<String> companies;

    @Override
    public String toString() {
        return "PostSearchForm{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", tags='" + tags + '\'' +
                ", companies=" + companies +
                '}';
    }

    public List<CompanyType> getCompanies() {
        if (StringUtils.isEmpty(companies)) {
            return null;
        }
        return companies.stream()
                .map(CompanyType::valueOf)
                .collect(Collectors.toList());
    }

}