package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.like.PostLike;
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
        PostLike like = postLikeDto.toLike();
        like.setUser(userPrincipal.toUserForId());
        likeWebRepository.save(like);
    }

    @Transactional
    public void delete(Long postId, UserPrincipal userPrincipal) {
        likeWebRepository.deleteByLinkPostAndUser(postId, userPrincipal.getId());
    }

}
