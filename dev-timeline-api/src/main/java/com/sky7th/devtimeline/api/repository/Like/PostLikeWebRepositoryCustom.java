package com.sky7th.devtimeline.api.repository.Like;

public interface PostLikeWebRepositoryCustom {

    void deleteByPostIdAndUserId(Long postId, Long userId);

}
