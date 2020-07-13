package com.sky7th.devtimeline.web.presentation.api;

import com.sky7th.devtimeline.web.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.web.security.CurrentUser;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.PostLikeService;
import com.sky7th.devtimeline.web.service.dto.PostLikeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.sky7th.devtimeline.web.presentation.api.dto.WebResponseStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/api/v1/like")
    public WebResponseDto<Object> saveLike(@RequestBody PostLikeDto likeDto, @CurrentUser UserPrincipal userPrincipal) {
        postLikeService.save(likeDto, userPrincipal);
        return WebResponseDto.builder().status(OK).build();
    }

    @DeleteMapping("/api/v1/like/{id}")
    public WebResponseDto<Object> deleteLike(@PathVariable("id") Long id,
                                             @CurrentUser UserPrincipal userPrincipal) {
        postLikeService.delete(id, userPrincipal);
        return WebResponseDto.builder().status(OK).build();
    }

}
