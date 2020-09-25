package com.sky7th.devtimeline.api.techpost.controller;

import com.sky7th.devtimeline.api.techpost.service.TechPostService;
import com.sky7th.devtimeline.core.domain.post.dto.PostPageResponseDto;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.techpost.dto.TechPostViewResponseDto;
import com.sky7th.devtimeline.core.domain.techpost.dto.TechPostViewResponseDtos;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechPostController {

    private final TechPostService techPostService;

    @GetMapping("/api/v1/tech-posts")
    public ResponseEntity<PostPageResponseDto<TechPostViewResponseDto>> list(@Valid PostSearchForm searchForm) {
        TechPostViewResponseDtos responseDtos = techPostService.findBySearchForm(searchForm);

        return ResponseEntity.ok(new PostPageResponseDto<>(
            responseDtos.getTechPostDtos(), responseDtos.getSearchCount(), searchForm.getOffset()));
    }
}
