package com.sky7th.devtimeline.api.postlike.repository;

public interface PostLikeWebRepositoryCustom {

    void deleteByPostIdAndUserId(Long postId, Long userId);

}
