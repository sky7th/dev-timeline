package com.sky7th.devtimeline.api.presentation.api;

import com.sky7th.devtimeline.api.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.api.security.CurrentUser;
import com.sky7th.devtimeline.api.security.UserPrincipal;
import com.sky7th.devtimeline.api.service.TechPostService;
import com.sky7th.devtimeline.api.service.dto.PostSearchForm;
import com.sky7th.devtimeline.api.service.dto.TechPostView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.sky7th.devtimeline.api.presentation.api.dto.WebResponseStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechPostApiController {

    private final TechPostService techPostService;

    @GetMapping("/api/v1/tech-posts")
    public WebResponseDto<Object> getTechPosts(@Valid PostSearchForm searchForm,
                                               @CurrentUser UserPrincipal userPrincipal) {
        Map<String, Object> templateData = new HashMap<>();
        TechPostView techPostView = techPostService.findBySearchForm(searchForm, userPrincipal);
        templateData.put("posts", techPostView.getTechPostDtos());
        templateData.put("offset", searchForm.getOffset() + techPostView.getTechPostDtos().size());
        templateData.put("postCounts", techPostView.getTechPostCounts());

        return WebResponseDto.builder().status(OK).data(templateData).build();
    }

}
