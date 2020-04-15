package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.linkpost.LinkPost;
import com.sky7th.devtimeline.web.repository.LinkPost.LinkPostWebRepository;
import com.sky7th.devtimeline.web.service.dto.LinkPostViewItems;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LinkPostService {

    private final LinkPostWebRepository linkPostWebRepository;

    @Transactional(readOnly = true)
    public LinkPostViewItems findBySearchForm(PostSearchForm postSearchForm) {
        List<LinkPost> recruitPosts = linkPostWebRepository.findBySearchForm(postSearchForm);
        long recruitPostCounts = linkPostWebRepository.countBySearchForm(postSearchForm);

        return new LinkPostViewItems(recruitPosts, recruitPostCounts);
    }

}
