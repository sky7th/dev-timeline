package com.sky7th.devtimeline.web.presentation.api;

import com.sky7th.devtimeline.web.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.web.service.TechPostService;
import com.sky7th.devtimeline.web.service.dto.TechPostViewItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sky7th.devtimeline.web.config.PagingConstant.PAGE_SIZE;
import static com.sky7th.devtimeline.web.presentation.api.dto.WebResponseStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechPostApiController {

    private final TechPostService techPostService;

    @GetMapping("/api/v1/tech-posts")
    public WebResponseDto<Object> getTechPosts(@RequestParam(value = "offset") Long offset,
                                           @RequestParam(value = "title", required = false) String title) {
        Map<String, Object> templateData = new HashMap<>();
        List<TechPostViewItem> items;

        if (title == null)
            items = techPostService.findAllLimitDesc(offset, PAGE_SIZE);
        else
            items = techPostService.findByTitleContainingLimitDesc(title, offset, PAGE_SIZE);

        templateData.put("techPosts", items);
        templateData.put("offset", (offset + 1) * items.size());

        return WebResponseDto.builder().status(OK).data(templateData).build();
    }

}
