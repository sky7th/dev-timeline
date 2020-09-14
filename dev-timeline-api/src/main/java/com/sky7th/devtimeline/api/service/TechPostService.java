package com.sky7th.devtimeline.api.service;

import com.sky7th.devtimeline.api.repository.techPost.TechPostWebRepository;
import com.sky7th.devtimeline.api.security.UserPrincipal;
import com.sky7th.devtimeline.api.service.dto.PostSearchForm;
import com.sky7th.devtimeline.api.service.dto.TechPostItem;
import com.sky7th.devtimeline.api.service.dto.TechPostView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TechPostService {

    private final TechPostWebRepository techPostWebRepository;

    @Transactional(readOnly = true)
    public TechPostView findBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal) {
        List<TechPostItem> techPosts = techPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(postSearchForm, userPrincipal);
        long techPostCounts = techPostWebRepository.countBySearchForm(postSearchForm, userPrincipal);

        return new TechPostView(techPosts, techPostCounts);
    }

}
