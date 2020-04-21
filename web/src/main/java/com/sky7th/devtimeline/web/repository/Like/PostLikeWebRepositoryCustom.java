package com.sky7th.devtimeline.web.repository.Like;

import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.core.domain.user.User;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;

import java.util.List;

public interface PostLikeWebRepositoryCustom {

    void deleteByLinkPostAndUser(Long linkPostId, Long userId);

}
