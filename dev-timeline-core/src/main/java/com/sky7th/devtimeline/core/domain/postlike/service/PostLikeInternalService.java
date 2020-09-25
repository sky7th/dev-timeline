package com.sky7th.devtimeline.core.domain.postlike.service;

import com.sky7th.devtimeline.core.domain.post.domain.Post;
import com.sky7th.devtimeline.core.domain.post.service.PostInternalService;
import com.sky7th.devtimeline.core.domain.postlike.domain.PostLike;
import com.sky7th.devtimeline.core.domain.postlike.domain.PostLikeRepository;
import com.sky7th.devtimeline.core.domain.postlike.exception.NotFoundPostLikeException;
import com.sky7th.devtimeline.core.domain.postlike.exception.UserAlreadyLikePostException;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import com.sky7th.devtimeline.core.domain.user.exception.MismatchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostLikeInternalService {

    private final PostLikeRepository postLikeRepository;
    private final PostInternalService postInternalService;

    @Transactional(readOnly = true)
    public PostLike findByPostIdAndUserId(Long postId, Long userId) {
        return postLikeRepository.findByPostIdAndUserId(postId, userId)
            .orElseThrow(NotFoundPostLikeException::new);
    }

    public void save(Long postId, UserContext userContext) {
        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, userContext.getId()).orElse(null);
        if (postLike != null) {
            throw new UserAlreadyLikePostException();
        }
        Post post = postInternalService.findById(postId);
        post.increaseLikeCount();
        postLikeRepository.save(new PostLike(post, userContext.getId()));
    }

    public void delete(Long postId, UserContext userContext) {
        Post post = postInternalService.findById(postId);
        post.decreaseLikeCount();
        PostLike postLike = findByPostIdAndUserId(postId, userContext.getId());
        postLikeRepository.delete(postLike);
    }

    public boolean isAuthor(Long postId, UserContext userContext) {
        PostLike postLike = findByPostIdAndUserId(postId, userContext.getId());
        if (!postLike.isAuthor(userContext.getId())) {
            throw new MismatchUserException();
        }
        return true;
    }
}
