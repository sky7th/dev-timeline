package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.web.exception.UnauthorizedException;
import com.sky7th.devtimeline.web.repository.LinkPost.LinkPostWebRepository;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.dto.LinkPostDto;
import com.sky7th.devtimeline.web.service.dto.LinkPostViewItem;
import com.sky7th.devtimeline.web.service.dto.LinkPostViewItems;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LinkPostService {

    private final TagService tagService;
    private final LinkPostWebRepository linkPostWebRepository;

    @Transactional(readOnly = true)
    public LinkPostViewItems findBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal) {
        List<LinkPostDto> recruitPosts = linkPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(postSearchForm, userPrincipal.getId());
        long recruitPostCounts = linkPostWebRepository.countBySearchForm(postSearchForm);

        return new LinkPostViewItems(recruitPosts, recruitPostCounts);
    }

    @Transactional(readOnly = true)
    public LinkPostViewItem findOne(Long id, UserPrincipal userPrincipal) {
        LinkPostDto linkPostDto = linkPostWebRepository.findWithLikeCountAndIsLikeByIdAndUserId(id, userPrincipal.getId()).orElseThrow(() -> new IllegalStateException("해당 link post는 존재하지 않습니다. id=" + id));

        return new LinkPostViewItem(linkPostDto);
    }

    @Transactional
    public void save(LinkPostViewItem linkPostViewItem, UserPrincipal userPrincipal) {
        LinkPost linkPost = linkPostViewItem.toLinkPost();
        linkPost.setUser(userPrincipal.toUserForId());
        linkPostWebRepository.save(linkPost);
    }

    @Transactional
    public void update(Long id, LinkPostViewItem linkPostViewItem, UserPrincipal userPrincipal) throws UnauthorizedException {
        LinkPost linkPost = linkPostWebRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 link post는 존재하지 않습니다. id=" + id));

        if (!linkPost.getUser().getId().equals(userPrincipal.getId())) {
            throw new UnauthorizedException("작성자 본인만 수정이 가능합니다.");
        }
        tagService.updateTags(linkPost, linkPostViewItem.getTags());
        linkPost.update(linkPostViewItem.getTitle(), linkPostViewItem.getContent(), linkPostViewItem.getLinkUrl());
    }

    @Transactional
    public void delete(Long id, UserPrincipal userPrincipal) throws UnauthorizedException {
        LinkPost linkPost = linkPostWebRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 link post는 존재하지 않습니다. id=" + id));

        if (!linkPost.getUser().getId().equals(userPrincipal.getId())) {
            throw new UnauthorizedException("작성자 본인만 삭제가 가능합니다.");
        }
        linkPostWebRepository.deleteById(id);
    }

}
