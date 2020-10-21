package com.sky7th.devtimeline.api.postlike.service;

import com.sky7th.devtimeline.post.postlike.service.PostLikeInternalService;
import com.sky7th.devtimeline.user.dto.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeInternalService postLikeInternalService;

    public void save(Long postId, UserContext userContext) {
        postLikeInternalService.save(postId, userContext.getId());
    }

    public void delete(Long postId, UserContext userContext) {
        postLikeInternalService.delete(postId, userContext.getId());
    }
}
