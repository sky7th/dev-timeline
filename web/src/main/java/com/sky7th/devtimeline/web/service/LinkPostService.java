package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.web.exception.UnauthorizedException;
import com.sky7th.devtimeline.web.repository.LinkPost.LinkPostWebRepository;
import com.sky7th.devtimeline.web.repository.comment.CommentWebRepository;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LinkPostService {

    private final TagService tagService;
    private final LinkPostWebRepository linkPostWebRepository;
    private final CommentWebRepository commentWebRepository;

    @Transactional(readOnly = true)
    public LinkPostView findBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal) {
        List<LinkPostItem> recruitPosts = linkPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(postSearchForm, userPrincipal==null?0:userPrincipal.getId());
        long recruitPostCounts = linkPostWebRepository.countBySearchForm(postSearchForm);

        return new LinkPostView(recruitPosts, recruitPostCounts);
    }

    @Transactional(readOnly = true)
    public LinkPostDetailDto findOne(Long id, UserPrincipal userPrincipal) {
        LinkPostItem linkPostItem = linkPostWebRepository.findWithLikeCountAndIsLikeByIdAndUserId(id, userPrincipal==null?0:userPrincipal.getId()).orElseThrow(() -> new IllegalStateException("해당 link post는 존재하지 않습니다. id=" + id));
        linkPostItem.setComments(commentWebRepository.findFromLastCommentIdToLimit(id, null, 5L));

        return new LinkPostDetailDto(linkPostItem);
    }

    @Transactional
    public void save(LinkPostDto linkPostDto, UserPrincipal userPrincipal) {
        LinkPost linkPost = linkPostDto.toLinkPost();
        linkPost.setUser(userPrincipal.toUserForId());
        linkPostWebRepository.save(linkPost);
    }

    @Transactional
    public void update(Long id, LinkPostDto linkPostDto, UserPrincipal userPrincipal) throws UnauthorizedException {
        LinkPost linkPost = linkPostWebRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 link post는 존재하지 않습니다. id=" + id));

        if (!linkPost.getUser().getId().equals(userPrincipal.getId())) {
            throw new UnauthorizedException("작성자 본인만 수정이 가능합니다.");
        }
        tagService.updateTags(linkPost.getPost(), linkPostDto.getTags());
        linkPost.update(linkPostDto.getTitle(), linkPostDto.getContent(), linkPostDto.getLinkUrl());
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
