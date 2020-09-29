package com.sky7th.devtimeline.api.postlike.service;

import com.sky7th.devtimeline.core.domain.postlike.service.PostLikeInternalService;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeInternalService postLikeInternalService;

    public void save(Long postId, UserContext userContext) {
        postLikeInternalService.save(postId, userContext);
    }

    public void delete(Long postId, UserContext userContext) {
        postLikeInternalService.delete(postId, userContext);
    }
}
