package com.sky7th.devtimeline.web.presentation.api;

import com.sky7th.devtimeline.web.exception.UnauthorizedException;
import com.sky7th.devtimeline.web.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.web.security.CurrentUser;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.LinkPostService;
import com.sky7th.devtimeline.web.service.dto.LinkPostDetailDto;
import com.sky7th.devtimeline.web.service.dto.LinkPostDto;
import com.sky7th.devtimeline.web.service.dto.LinkPostView;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sky7th.devtimeline.web.presentation.api.dto.WebResponseStatus.FORBIDDEN;
import static com.sky7th.devtimeline.web.presentation.api.dto.WebResponseStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LinkPostApiController {

    private final LinkPostService linkPostService;

    @GetMapping("/api/v1/link-posts")
    public WebResponseDto<Object> getLinkPosts(@Valid PostSearchForm searchForm,
                                               @CurrentUser UserPrincipal userPrincipal) {
        Map<String, Object> templateData = new HashMap<>();
        LinkPostView linkPostView = linkPostService.findBySearchForm(searchForm, userPrincipal);
        templateData.put("posts", linkPostView.getLinkPostDtos());
        templateData.put("offset", searchForm.getOffset() + linkPostView.getLinkPostDtos().size());
        templateData.put("postCounts", linkPostView.getLinkPostCounts());

        return WebResponseDto.builder().status(OK).data(templateData).build();
    }

    @GetMapping("/api/v1/link-posts/{id}")
    public WebResponseDto<Object> getLinkPost(@PathVariable(name = "id") Long id,
                                              @CurrentUser UserPrincipal userPrincipal) {
        LinkPostDetailDto linkPostViewItem = linkPostService.findOne(id, userPrincipal);

        return WebResponseDto.builder().status(OK).data(linkPostViewItem).build();
    }

    @PostMapping("/api/v1/link-posts")
    public WebResponseDto<Object> saveLinkPost(@RequestBody LinkPostDto linkPostDto, @CurrentUser UserPrincipal userPrincipal) {
        linkPostService.save(linkPostDto, userPrincipal);
        return WebResponseDto.builder().status(OK).build();
    }

    @PutMapping("/api/v1/link-posts/{id}")
    public WebResponseDto<Object> updateLinkPost(@PathVariable(value = "id") Long id,
                                                 @RequestBody LinkPostDto linkPostDto,
                                                 @CurrentUser UserPrincipal userPrincipal) {
        try {
            linkPostService.update(id, linkPostDto, userPrincipal);
        } catch (UnauthorizedException e) {
            return WebResponseDto.builder().status(FORBIDDEN).build();
        }

        return WebResponseDto.builder().status(OK).build();
    }

    @DeleteMapping("/api/v1/link-posts/{id}")
    public WebResponseDto<Object> deleteLinkPost(@PathVariable(value = "id") Long id,
                                                 @CurrentUser UserPrincipal userPrincipal) {
        try {
            linkPostService.delete(id, userPrincipal);
        } catch (UnauthorizedException e) {
            return WebResponseDto.builder().status(FORBIDDEN).build();
        }

        return WebResponseDto.builder().status(OK).build();
    }

}
