package com.sky7th.devtimeline.crawlpost.company.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

  @Query(value = "SELECT c FROM Company c LEFT JOIN CompanyUrl cu ON c.id = cu.company.id WHERE cu.companyUrlType = :companyUrlType")
  List<Company> findAllByCompanyUrlType(CompanyUrlType companyUrlType);
}