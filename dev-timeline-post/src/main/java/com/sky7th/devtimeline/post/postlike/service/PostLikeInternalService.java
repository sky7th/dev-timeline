package com.sky7th.devtimeline.post.postlike.service;

import com.sky7th.devtimeline.post.post.domain.Post;
import com.sky7th.devtimeline.post.post.service.PostInternalService;
import com.sky7th.devtimeline.post.postlike.domain.PostLike;
import com.sky7th.devtimeline.post.postlike.domain.PostLikeRepository;
import com.sky7th.devtimeline.post.postlike.exception.NotFoundPostLikeException;
import com.sky7th.devtimeline.post.postlike.exception.UserAlreadyLikePostException;
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

    public void save(Long postId, Long userId) {
        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, userId).orElse(null);
        if (postLike != null) {
            throw new UserAlreadyLikePostException();
        }
        Post post = postInternalService.findById(postId);
        post.increaseLikeCount();
        postLikeRepository.save(new PostLike(post, userId));
    }

    public void delete(Long postId, Long userId) {
        PostLike postLike = findByPostIdAndUserId(postId, userId);
        postLike.getPost().decreaseLikeCount();
        postLikeRepository.delete(postLike);
    }
}
