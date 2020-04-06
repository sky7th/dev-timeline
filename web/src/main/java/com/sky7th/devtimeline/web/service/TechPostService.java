package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.techpost.TechPost;
import com.sky7th.devtimeline.web.repository.techPost.TechPostWebRepository;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import com.sky7th.devtimeline.web.service.dto.TechPostViewItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TechPostService {

    private final TechPostWebRepository techPostWebRepository;

    @Transactional(readOnly = true)
    public List<TechPostViewItem> findBySearchForm(PostSearchForm postSearchForm) {
        List<TechPost> techPosts = techPostWebRepository.findBySearchForm(postSearchForm);
        List<TechPostViewItem> techPostViewItems = new ArrayList<>();

        for (TechPost techPost : techPosts) {
            techPostViewItems.add(new TechPostViewItem(techPost));
        }
        return techPostViewItems;
    }

}
