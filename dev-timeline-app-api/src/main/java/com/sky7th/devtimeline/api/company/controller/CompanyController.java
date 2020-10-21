package com.sky7th.devtimeline.api.company.controller;

import com.sky7th.devtimeline.api.company.service.CompanyService;
import com.sky7th.devtimeline.crawlpost.company.dto.CompanyResponseDtos;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrlType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/api/v1/companies")
    public ResponseEntity<CompanyResponseDtos> getCompany(@RequestParam(value = "companyUrlType") CompanyUrlType urlType) {
        return ResponseEntity.ok(companyService.findAllByCompanyUrlType(urlType));
    }
}
