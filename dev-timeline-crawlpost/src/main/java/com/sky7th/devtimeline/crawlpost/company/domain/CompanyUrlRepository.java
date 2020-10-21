package com.sky7th.devtimeline.crawlpost.company.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyUrlRepository extends JpaRepository<CompanyUrl,Long> {
}