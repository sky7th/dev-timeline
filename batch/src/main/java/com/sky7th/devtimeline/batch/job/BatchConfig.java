package com.sky7th.devtimeline.batch.job;

import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.CrawlingService;
import com.sky7th.devtimeline.batch.dto.CompanyDto;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;
import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchConfig {

    private static final String JOB_NAME = "crawlingBatch";
    private static final String BEAN_PREFIX = JOB_NAME + "_";
    private final int CHUNK_SIZE = 10;

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CrawlingService crawlingService;
    private final RecruitPostRepository recruitPostRepository;

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step())
                .build();
    }

    @Bean(BEAN_PREFIX + "step")
    @JobScope
    public Step step() {
        return stepBuilderFactory.get("crawlingStep")
                .<CompanyUrl, List<CrawlingDto>>chunk(CHUNK_SIZE)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean(BEAN_PREFIX + "reader")
    public JpaPagingItemReader<CompanyUrl> reader() {
        return new JpaPagingItemReaderBuilder<CompanyUrl>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("SELECT u FROM company_url u")
                .build();
    }

    @Bean(BEAN_PREFIX + "processor")
    public ItemProcessor<CompanyUrl, List<CrawlingDto>> processor() {
        return companyUrl -> crawlingService.crawling(CompanyDto.builder()
                    .companyUrl(companyUrl)
                    .build());
    }

    @Bean(BEAN_PREFIX + "writer")
    @Transactional
    public ItemWriter<List<CrawlingDto>> writer() {
        return crawlingResults -> {
            if (isEmpty(crawlingResults)) {
                log.info(">>>>>>>> writer 종료 (크롤링 도중 문제가 생김)");
                return;
            }
            List<RecruitPost> existRecruitPosts = recruitPostRepository.findAll();
            Map<String, Integer> addedPostCountMap = new HashMap<>();
            int addedPostCount = 0;

            for (List<CrawlingDto> crawlingResult : crawlingResults) {
                for (CrawlingDto crawlingDto : crawlingResult) {

                    long existCount = existRecruitPosts.stream()
                            .filter(existRecruitPost -> existRecruitPost.isEqual(crawlingDto.toString()))
                            .count();

                    if (existCount == 0) {
                        RecruitPost newRecruitPost = crawlingDto.toRecruitPost(crawlingDto);
                        recruitPostRepository.save(newRecruitPost);

                        updateAddedPostCountMap(addedPostCountMap, newRecruitPost);
                        addedPostCount += 1;
                    }
                }
            }
            for ( String key : addedPostCountMap.keySet() ) {
                log.info(">>>>>>>> {}: {} 개 새로 추가", key, addedPostCountMap.get(key));
            }

            log.info(">>>>>>>> 신규 크롤링 정보 {} 개 추가 성공", addedPostCount);
        };
    }

    private void updateAddedPostCountMap(Map<String, Integer> map, RecruitPost recruitPost) {
        String key = recruitPost.getCompanyUrl().getCompany().getCompanyType().getName()
                +" "+recruitPost.getCompanyUrl().getCompanyUrlType().getName();

        if (map.containsKey(key))
            map.put(key, map.get(key) + 1);
        else
            map.put(key, 1);
    }

    private boolean isEmpty(List<? extends List<CrawlingDto>> crawlingResults) {
        for (List<CrawlingDto> crawlingDtos : crawlingResults) {
            if (crawlingDtos.isEmpty())
                return true;
        }
        return false;
    }

}