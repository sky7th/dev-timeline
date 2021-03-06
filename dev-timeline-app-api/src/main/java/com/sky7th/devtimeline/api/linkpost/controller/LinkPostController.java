package com.sky7th.devtimeline.api.linkpost.controller;

import com.sky7th.devtimeline.api.linkpost.dto.LinkPostViewDetailResponseDto;
import com.sky7th.devtimeline.api.linkpost.dto.LinkPostViewResponseDto;
import com.sky7th.devtimeline.api.linkpost.dto.LinkPostViewResponseDtos;
import com.sky7th.devtimeline.api.linkpost.service.LinkPostService;
import com.sky7th.devtimeline.generalpost.linkpost.dto.LinkPostRequestDto;
import com.sky7th.devtimeline.generalpost.linkpost.dto.LinkPostResponseDto;
import com.sky7th.devtimeline.generalpost.linkpost.dto.LinkPostSearchForm;
import com.sky7th.devtimeline.post.post.dto.PostPageResponseDto;
import com.sky7th.devtimeline.user.dto.UserContext;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LinkPostController {

    private final LinkPostService linkPostService;

    @GetMapping("/api/v1/link-posts")
    public ResponseEntity<PostPageResponseDto<LinkPostViewResponseDto>> list(@Valid LinkPostSearchForm searchForm) {
        LinkPostViewResponseDtos linkPostView = linkPostService.findBySearchForm(searchForm);

        return ResponseEntity.ok(new PostPageResponseDto<>(
            linkPostView.getLinkPosts(), linkPostView.getSearchCount(), searchForm.getOffset()));
    }

    @GetMapping("/api/v1/link-posts/{postId}")
    public ResponseEntity<LinkPostViewDetailResponseDto> show(@PathVariable Long postId) {
        return ResponseEntity.ok(linkPostService.findOne(postId));
    }

    @PostMapping("/api/v1/link-posts")
    public ResponseEntity<LinkPostResponseDto> save(@RequestBody LinkPostRequestDto requestDto, UserContext userContext) {
        return ResponseEntity.ok(linkPostService.save(requestDto, userContext));
    }

    @PutMapping("/api/v1/link-posts/{postId}")
    public ResponseEntity<LinkPostResponseDto> update(@PathVariable Long postId,
        @RequestBody LinkPostRequestDto requestDto, UserContext userContext) {
        return ResponseEntity.ok(linkPostService.update(postId, requestDto, userContext));
    }

    @DeleteMapping("/api/v1/link-posts/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId, UserContext userContext) {
        linkPostService.delete(postId, userContext);
        return ResponseEntity.ok().build();
    }

}
