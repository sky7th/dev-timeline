package com.sky7th.devtimeline.api.comment.controller;

import com.sky7th.devtimeline.api.comment.service.CommentService;
import com.sky7th.devtimeline.core.domain.comment.dto.CommentRequestDto;
import com.sky7th.devtimeline.core.domain.comment.dto.CommentResponseDto;
import com.sky7th.devtimeline.core.domain.comment.dto.CommentResponseDtos;
import com.sky7th.devtimeline.core.domain.comment.dto.CommentUpdateRequestDto;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDtos> list(@PathVariable Long postId,
                                              @RequestParam(value = "lastCommentId") Long lastCommentId,
                                              @RequestParam(value = "limit") Long limit) {
        return ResponseEntity.ok(commentService.findByOffsetAndLimit(postId, lastCommentId, limit));
    }

    @PostMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> save(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto,
            UserContext userContext) {
        return ResponseEntity.ok(commentService.save(postId, requestDto, userContext));
    }

    @PutMapping("/api/v1/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto requestDto,
            UserContext userContext) {
        return ResponseEntity.ok(commentService.update(commentId, requestDto, userContext));
    }

    @DeleteMapping("/api/v1/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId,UserContext userContext) {
        commentService.delete(commentId, userContext);
        return ResponseEntity.ok().build();
    }
}
