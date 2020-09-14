package com.sky7th.devtimeline.api.controller;

import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;
import com.sky7th.devtimeline.api.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.api.service.CompanyService;
import com.sky7th.devtimeline.api.service.dto.CompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sky7th.devtimeline.api.presentation.api.dto.WebResponseStatus.OK;

@RequiredArgsConstructor
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/api/v1/company")
    public WebResponseDto<Object> getCompany(@RequestParam(value = "companyUrlType") String urlType) {
        List<CompanyDto> companies = companyService.findByCompanyUrlType(CompanyUrlType.valueOf(urlType));
        return WebResponseDto.builder().status(OK).data(companies).build();
    }
}
