package com.sky7th.devtimeline.web.presentation.api;

import com.sky7th.devtimeline.web.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.web.service.RecruitPostService;
import com.sky7th.devtimeline.web.service.dto.RecruitPostViewItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sky7th.devtimeline.web.config.PagingConstant.PAGE_SIZE;
import static com.sky7th.devtimeline.web.presentation.api.dto.WebResponseStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RecruitPostApiController {

    private final RecruitPostService recruitPostService;

    @GetMapping("/api/v1/articles/{offset}")
    public WebResponseDto<Object> readMore(@PathVariable(value = "offset") Long offset) {
        Map<String, Object> templateData = new HashMap<>();
        List<RecruitPostViewItem> items = recruitPostService.findAllLimitDesc(offset, PAGE_SIZE);
        templateData.put("recruitPosts", items);
        templateData.put("offset", (offset + 1) * items.size());

        return WebResponseDto.builder().status(OK).data(templateData).build();
    }

}