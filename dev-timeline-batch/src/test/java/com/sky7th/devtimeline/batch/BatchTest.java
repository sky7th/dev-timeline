//package com.sky7th.devtimeline.batch;
//
//import com.sky7th.devtimeline.batch.repository.recruitpost.RecruitPostBatchRepository;
//import com.sky7th.devtimeline.core.domain.company.domain.Company;
//import com.sky7th.devtimeline.core.domain.company.domain.CompanyRepository;
//import com.sky7th.devtimeline.core.domain.company.domain.CompanyType;
//import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrl;
//import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrlRepository;
//import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrlType;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.batch.core.BatchStatus;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//
//import static junit.framework.TestCase.assertTrue;
//import static org.junit.Assert.assertEquals;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//class BatchTest {
//
//    @Autowired
//    JobLauncherTestUtils jobLauncherTestUtils;
//
//    @Autowired
//    CompanyRepository companyRepository;
//
//    @Autowired
//    CompanyUrlRepository companyUrlRepository;
//
//    @Autowired
//    RecruitPostBatchRepository recruitPostBatchRepository;
//
//    @Test
//    void test_batch() throws Exception {
//
//        //given
//        Company kakaoCompany = Company.builder()
//                .companyType(CompanyType.KAKAO)
//                .build();
//
//        Company naverCompany = Company.builder()
//                .companyType(CompanyType.NAVER)
//                .build();
//
//        companyRepository.save(kakaoCompany);
//        companyRepository.save(naverCompany);
//
//        CompanyUrl kakaoCompanyUrl = CompanyUrl.builder()
//                .companyUrlType(CompanyUrlType.RECRUIT)
//                .url("https://careers.kakao.com/jobs")
//                .build();
//        kakaoCompanyUrl.setCompany(companyRepository.findByCompanyType(CompanyType.KAKAO));
//
//        CompanyUrl naverCompanyUrl = CompanyUrl.builder()
//                .companyUrlType(CompanyUrlType.RECRUIT)
//                .url("https://recruit.navercorp.com/naver/job/list/developer")
//                .build();
//        naverCompanyUrl.setCompany(companyRepository.findByCompanyType(CompanyType.NAVER));
//
//        companyUrlRepository.save(kakaoCompanyUrl);
//        companyUrlRepository.save(naverCompanyUrl);
//
//        JobParameters requestDate = new JobParametersBuilder()
//                .addDate("requestDate", new Date())
//                .toJobParameters();
//
//        //when
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(requestDate);
//
//        //then
//        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
//
//        assertTrue(recruitPostBatchRepository.count() > 0);
//
//    }
//
//}
