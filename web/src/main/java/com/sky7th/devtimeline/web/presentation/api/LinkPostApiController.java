package com.sky7th.devtimeline.web.presentation.api;

import com.sky7th.devtimeline.web.exception.UnauthorizedException;
import com.sky7th.devtimeline.web.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.web.security.CurrentUser;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.LinkPostService;
import com.sky7th.devtimeline.web.service.dto.LinkPostViewItem;
import com.sky7th.devtimeline.web.service.dto.LinkPostViewItems;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
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
        LinkPostViewItems linkPostViewItems = linkPostService.findBySearchForm(searchForm, userPrincipal);

        templateData.put("posts", linkPostViewItems.getLinkPostItems());
        templateData.put("offset", searchForm.getOffset() + linkPostViewItems.getLinkPostItems().size());
        templateData.put("postCounts", linkPostViewItems.getLinkPostCounts());

        return WebResponseDto.builder().status(OK).data(templateData).build();
    }

    @GetMapping("/api/v1/link-posts/{id}")
    public WebResponseDto<Object> getLinkPost(@PathVariable(name = "id") Long id,
                                              @CurrentUser UserPrincipal userPrincipal) {
        LinkPostViewItem linkPostViewItem = linkPostService.findOne(id, userPrincipal);

        return WebResponseDto.builder().status(OK).data(linkPostViewItem).build();
    }

    @PostMapping("/api/v1/link-posts")
    public WebResponseDto<Object> saveLinkPost(@RequestBody LinkPostViewItem linkPostViewItem, @CurrentUser UserPrincipal userPrincipal) {
        linkPostService.save(linkPostViewItem, userPrincipal);
        return WebResponseDto.builder().status(OK).build();
    }

    @PutMapping("/api/v1/link-posts/{id}")
    public WebResponseDto<Object> updateLinkPost(@PathVariable(value = "id") Long id,
                                                 @RequestBody LinkPostViewItem linkPostViewItem,
                                                 @CurrentUser UserPrincipal userPrincipal) {
        try {
            linkPostService.update(id, linkPostViewItem, userPrincipal);
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
