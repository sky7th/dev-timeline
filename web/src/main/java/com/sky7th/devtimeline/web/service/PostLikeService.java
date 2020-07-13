package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.like.PostLike;
import com.sky7th.devtimeline.web.exception.UnauthorizedException;
import com.sky7th.devtimeline.web.repository.Like.PostLikeWebRepository;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.dto.PostLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeWebRepository likeWebRepository;

    @Transactional
    public void save(PostLikeDto postLikeDto, UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            throw new UnauthorizedException("로그인 후 이용 가능합니다.");
        }
        PostLike like = postLikeDto.toLike();
        like.setUser(userPrincipal.toUserForId());
        likeWebRepository.save(like);
    }

    @Transactional
    public void delete(Long postId, UserPrincipal userPrincipal) {
        likeWebRepository.deleteByPostIdAndUserId(postId, userPrincipal.getId());
    }

}
