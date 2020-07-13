package com.sky7th.devtimeline.web.presentation.api;

import com.sky7th.devtimeline.web.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.web.security.CurrentUser;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.RecruitPostService;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import com.sky7th.devtimeline.web.service.dto.RecruitPostDto;
import com.sky7th.devtimeline.web.service.dto.RecruitPostView;
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
public class RecruitPostApiController {

    private final RecruitPostService recruitPostService;

    @GetMapping("/api/v1/recruit-posts")
    public WebResponseDto<Object> getRecruitPosts(@Valid PostSearchForm searchForm,
                                                  @CurrentUser UserPrincipal userPrincipal) {
        Map<String, Object> templateData = new HashMap<>();
        RecruitPostView recruitPostView = recruitPostService.findBySearchForm(searchForm, userPrincipal);
        templateData.put("posts", recruitPostView.getRecruitPostDtos());
        templateData.put("offset", searchForm.getOffset() + recruitPostView.getRecruitPostDtos().size());
        templateData.put("postCounts", recruitPostView.getRecruitPostCounts());

        return WebResponseDto.builder().status(OK).data(templateData).build();
    }

}