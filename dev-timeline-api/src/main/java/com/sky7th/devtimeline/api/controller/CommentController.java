package com.sky7th.devtimeline.api.controller;

import com.sky7th.devtimeline.api.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.api.security.CurrentUser;
import com.sky7th.devtimeline.api.security.UserPrincipal;
import com.sky7th.devtimeline.api.service.CommentService;
import com.sky7th.devtimeline.api.service.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sky7th.devtimeline.api.presentation.api.dto.WebResponseStatus.OK;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/api/v1/link-posts/comment")
    public WebResponseDto<Object> getComments(@RequestParam(value = "postId") Long postId,
                                              @RequestParam(value = "lastCommentId") Long lastCommentId,
                                              @RequestParam(value = "limit") Long limit) {
        List<CommentDto> comments = commentService.findByOffsetAndLimit(postId, lastCommentId, limit);
        return WebResponseDto.builder().status(OK).data(comments).build();
    }

    @PostMapping("/api/v1/link-posts/comment")
    public WebResponseDto<Object> saveLike(@RequestBody CommentDto commentDto, @CurrentUser UserPrincipal userPrincipal) {
        CommentDto comment = commentService.save(commentDto, userPrincipal);
        return WebResponseDto.builder().status(OK).data(comment).build();
    }

    @DeleteMapping("/api/v1/link-posts/comment/{id}")
    public WebResponseDto<Object> deleteLike(@PathVariable("id") Long id,
                                             @CurrentUser UserPrincipal userPrincipal) {
        commentService.delete(id, userPrincipal);
        return WebResponseDto.builder().status(OK).build();
    }
}
