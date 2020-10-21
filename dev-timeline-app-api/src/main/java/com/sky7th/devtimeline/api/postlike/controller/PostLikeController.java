package com.sky7th.devtimeline.api.postlike.controller;

import com.sky7th.devtimeline.api.postlike.service.PostLikeService;
import com.sky7th.devtimeline.user.dto.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/api/v1/posts/{postId}/likes")
    public ResponseEntity<Void> save(@PathVariable Long postId, UserContext userContext) {
        postLikeService.save(postId, userContext);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/posts/{postId}/likes")
    public ResponseEntity<Void> delete(@PathVariable Long postId, UserContext userContext) {
        postLikeService.delete(postId, userContext);
        return ResponseEntity.ok().build();
    }
}
