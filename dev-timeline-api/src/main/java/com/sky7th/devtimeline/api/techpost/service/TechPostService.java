package com.sky7th.devtimeline.api.techpost.service;

import com.sky7th.devtimeline.api.techpost.repository.TechPostWebRepository;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.techpost.dto.TechPostItem;
import com.sky7th.devtimeline.core.domain.techpost.dto.TechPostViewResponseDtos;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TechPostService {

    private final TechPostWebRepository techPostWebRepository;

    @Transactional(readOnly = true)
    public TechPostViewResponseDtos findBySearchForm(PostSearchForm postSearchForm, UserContext userContext) {
        List<TechPostItem> techPosts = techPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(postSearchForm, userContext);
        long techPostCounts = techPostWebRepository.countBySearchForm(postSearchForm, userContext);

        return TechPostViewResponseDtos.of(techPosts, techPostCounts);
    }

}
