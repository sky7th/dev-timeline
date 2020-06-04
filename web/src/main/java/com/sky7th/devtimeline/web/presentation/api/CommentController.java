package com.sky7th.devtimeline.web.presentation.api;

import com.sky7th.devtimeline.core.domain.comment.Comment;
import com.sky7th.devtimeline.web.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.web.security.CurrentUser;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.CommentService;
import com.sky7th.devtimeline.web.service.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.sky7th.devtimeline.web.presentation.api.dto.WebResponseStatus.OK;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/v1/link-posts/comment")
    public WebResponseDto<Object> saveLike(@RequestBody CommentDto commentDto, @CurrentUser UserPrincipal userPrincipal) {
        commentService.save(commentDto, userPrincipal);
        return WebResponseDto.builder().status(OK).build();
    }

    @DeleteMapping("/api/v1/link-posts/comment/{id}")
    public WebResponseDto<Object> deleteLike(@PathVariable("id") Long id,
                                             @CurrentUser UserPrincipal userPrincipal) {
        commentService.delete(id, userPrincipal);
        return WebResponseDto.builder().status(OK).build();
    }
}
