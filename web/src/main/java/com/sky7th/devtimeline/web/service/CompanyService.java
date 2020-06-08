package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;
import com.sky7th.devtimeline.web.repository.company.CompanyWebRepository;
import com.sky7th.devtimeline.web.service.dto.CompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyWebRepository companyWebRepository;

    public List<CompanyDto> findByCompanyUrlType(CompanyUrlType urlType) {
        return companyWebRepository.findByCompanyUrlType(urlType).stream()
                .map(CompanyDto::new)
                .collect(Collectors.toList());
    }
}
