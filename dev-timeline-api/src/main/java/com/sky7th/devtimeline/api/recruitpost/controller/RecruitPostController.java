package com.sky7th.devtimeline.api.recruitpost.controller;

import com.sky7th.devtimeline.api.recruitpost.service.RecruitPostService;
import com.sky7th.devtimeline.core.domain.post.dto.PostPageResponseDto;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.recruitpost.dto.RecruitPostViewResponseDto;
import com.sky7th.devtimeline.core.domain.recruitpost.dto.RecruitPostViewResponseDtos;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
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
    public ResponseEntity<PostPageResponseDto<RecruitPostViewResponseDto>> list(
        @Valid PostSearchForm searchForm, UserContext userContext) {
        RecruitPostViewResponseDtos responseDtos = recruitPostService.findBySearchForm(searchForm, userContext);

        return ResponseEntity.ok(new PostPageResponseDto<>(
            responseDtos.getRecruitPosts(), responseDtos.getSearchCount(), searchForm.getOffset()));
    }
}