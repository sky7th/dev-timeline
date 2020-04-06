package com.sky7th.devtimeline.web.presentation.api;

import com.sky7th.devtimeline.web.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.web.service.TechPostService;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import com.sky7th.devtimeline.web.service.dto.TechPostViewItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sky7th.devtimeline.web.presentation.api.dto.WebResponseStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechPostApiController {

    private final TechPostService techPostService;

    @GetMapping("/api/v1/tech-posts")
    public WebResponseDto<Object> getTechPosts(@Valid PostSearchForm searchForm) {
        Map<String, Object> templateData = new HashMap<>();
        List<TechPostViewItem> items;

        items = techPostService.findBySearchForm(searchForm);

        templateData.put("techPosts", items);
        templateData.put("offset", searchForm.getOffset() + items.size());

        return WebResponseDto.builder().status(OK).data(templateData).build();
    }

}
