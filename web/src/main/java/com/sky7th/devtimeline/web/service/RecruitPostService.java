package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;
import com.sky7th.devtimeline.web.repository.recruitPost.RecruitPostWebRepository;
import com.sky7th.devtimeline.web.service.dto.RecruitPostViewItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecruitPostService {

    public final RecruitPostWebRepository recruitPostWebRepository;

    @Transactional(readOnly = true)
    public List<RecruitPostViewItem> findAllLimitDesc(long offset, long limit) {
        List<RecruitPost> recruitPosts = recruitPostWebRepository.findAllLimitDesc(offset, limit);
        List<RecruitPostViewItem> recruitPostViewItems = new ArrayList<>();

        for (RecruitPost recruitPost : recruitPosts) {
            recruitPostViewItems.add(new RecruitPostViewItem(recruitPost));
        }
        return recruitPostViewItems;
    }

}
