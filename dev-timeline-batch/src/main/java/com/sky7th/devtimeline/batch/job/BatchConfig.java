package com.sky7th.devtimeline.batch.job;

import com.sky7th.devtimeline.batch.dto.CompanyDto;
import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.CrawlingService;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrl;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrlType;
import com.sky7th.devtimeline.core.domain.post.domain.Post;
import com.sky7th.devtimeline.core.domain.post.domain.PostRepository;
import com.sky7th.devtimeline.core.domain.post.domain.PostType;
import com.sky7th.devtimeline.core.domain.recruitpost.domain.RecruitPost;
import com.sky7th.devtimeline.core.domain.recruitpost.domain.RecruitPostRepository;
import com.sky7th.devtimeline.core.domain.techpost.domain.TechPost;
import com.sky7th.devtimeline.core.domain.techpost.domain.TechPostRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
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

    private final TechPostRepository techPostRepository;
    private final RecruitPostRepository recruitPostRepository;
    private final PostRepository postRepository;

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
                .queryString("SELECT u FROM CompanyUrl u JOIN FETCH u.company")
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

            List<TechPost> techPosts = techPostRepository.findAll();
            Map<String, TechPost> techPostMap = techPosts.stream()
                .collect(Collectors.toMap(TechPost::getPostCrawlId, techPost -> techPost));
            List<RecruitPost> recruitPosts = recruitPostRepository.findAll();
            Map<String, RecruitPost> recruitPostMap = recruitPosts.stream()
                .collect(Collectors.toMap(RecruitPost::getPostCrawlId, recruitPost -> recruitPost));

            Map<String, Integer> addedPostCountMap = new HashMap<>();
            int addedPostCount = 0;

            Map<String, Integer> updatedPostCountMap = new HashMap<>();
            int updatedPostCount = 0;

            Set<RecruitPost> crawlingRecruitPosts = new HashSet<>();
            Set<TechPost> crawlingTechPosts = new HashSet<>();

            for (List<CrawlingDto> crawlingResult : crawlingResults) {
                Set<CrawlingDto> crawlingDtos = new LinkedHashSet<>(crawlingResult);
                for (CrawlingDto crawlingDto : crawlingDtos) {
                    if (crawlingDto.isCompanyUrlType(CompanyUrlType.RECRUIT)) {
                        RecruitPost recruitPost = crawlingDto.toRecruitPost();
                        crawlingRecruitPosts.add(recruitPost);
                        String crawlId = recruitPost.getPostCrawlId();

                        if (recruitPostMap.containsKey(crawlId)) {
                            if (!recruitPost.equals(recruitPostMap.get(crawlId))) {
                                recruitPostMap.get(crawlId).update(recruitPost.getTitle(), recruitPost.getClosingDate());
                                updatePostCountMap(updatedPostCountMap, crawlingDto.getCompanyUrl());
                                updatedPostCount += 1;
                            }
                        } else {
                            recruitPostRepository.save(crawlingDto.toRecruitPost());
                            postRepository.save(new Post(PostType.RECRUIT_POST, crawlingDto.getCrawlId()));
                            updatePostCountMap(addedPostCountMap, crawlingDto.getCompanyUrl());
                            addedPostCount += 1;
                        }

                    } else if (crawlingDto.isCompanyUrlType(CompanyUrlType.TECH)) {
                        TechPost techPost = crawlingDto.toTechPost();
                        crawlingTechPosts.add(techPost);
                        String crawlId = techPost.getPostCrawlId();

                        if (techPostMap.containsKey(crawlId)) {
                            if (!techPost.equals(techPostMap.get(crawlId))) {
                                techPostMap.get(crawlId).update(techPost.getTitle(), techPost.getDate(), techPost.getThumbnailUrl());
                                updatePostCountMap(updatedPostCountMap, crawlingDto.getCompanyUrl());
                                updatedPostCount += 1;
                            }
                        } else {
                            techPostRepository.save(crawlingDto.toTechPost());
                            postRepository.save(new Post(PostType.TECH_POST, crawlingDto.getCrawlId()));
                            updatePostCountMap(addedPostCountMap, crawlingDto.getCompanyUrl());
                            addedPostCount += 1;
                        }
                    }
                }
            }

            for (RecruitPost recruitPost : recruitPosts) {
                if (!crawlingRecruitPosts.contains(recruitPost)) {
                    recruitPostRepository.delete(recruitPost);
                }
            }

            for (TechPost techPost : techPosts) {
                if (!crawlingTechPosts.contains(techPost)) {
                    techPostRepository.delete(techPost);
                }
            }

            for (String key : addedPostCountMap.keySet()) {
                log.info(">>>>>>>> {}: {} 개 새로 추가", key, addedPostCountMap.get(key));
            }

            for (String key : updatedPostCountMap.keySet()) {
                log.info(">>>>>>>> {}: {} 개 내용 변경", key, updatedPostCountMap.get(key));
            }

            log.info(">>>>>>>> 신규 크롤링 정보 {} 개 추가", addedPostCount);
            log.info(">>>>>>>> 신규 크롤링 정보 {} 개 내용 변경", updatedPostCount);
        };
    }

    private void updatePostCountMap(Map<String, Integer> map, CompanyUrl companyUrl) {
        String key = companyUrl.getCompany().getCompanyType().getName()
                +" "+companyUrl.getCompanyUrlType().getName();

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