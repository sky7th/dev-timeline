package com.sky7th.devtimeline.core.domain.company.domain;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyUrlRepository extends JpaRepository<CompanyUrl,Long> {

  @EntityGraph(attributePaths = "company")
  List<CompanyUrl> findAllByCompanyUrlType(CompanyUrlType companyUrlType);
}