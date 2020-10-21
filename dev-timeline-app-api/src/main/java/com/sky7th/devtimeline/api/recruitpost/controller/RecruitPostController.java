package com.sky7th.devtimeline.api.recruitpost.controller;

import com.sky7th.devtimeline.api.recruitpost.service.RecruitPostService;
import com.sky7th.devtimeline.crawlpost.CrawlPostSearchForm;
import com.sky7th.devtimeline.crawlpost.recruitpost.dto.RecruitPostViewResponseDto;
import com.sky7th.devtimeline.crawlpost.recruitpost.dto.RecruitPostViewResponseDtos;
import com.sky7th.devtimeline.post.post.dto.PostPageResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RecruitPostController {

    private final RecruitPostService recruitPostService;

    @GetMapping("/api/v1/recruit-posts")
    public ResponseEntity<PostPageResponseDto<RecruitPostViewResponseDto>> list(@Valid CrawlPostSearchForm searchForm) {
        RecruitPostViewResponseDtos responseDtos = recruitPostService.findBySearchForm(searchForm);

        return ResponseEntity.ok(new PostPageResponseDto<>(
            responseDtos.getRecruitPosts(), responseDtos.getSearchCount(), searchForm.getOffset()));
    }
}