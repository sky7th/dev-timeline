package com.sky7th.devtimeline.web.repository.Like;

public interface PostLikeWebRepositoryCustom {

    void deleteByPostIdAndUserId(Long postId, Long userId);

}
