package com.sky7th.devtimeline.api.linkpost.service;

import com.sky7th.devtimeline.api.comment.repository.CommentWebRepository;
import com.sky7th.devtimeline.api.linkpost.repository.LinkPostWebRepository;
import com.sky7th.devtimeline.api.security.exception.UnauthorizedException;
import com.sky7th.devtimeline.core.domain.linkpost.dto.LinkPostViewDetailResponseDto;
import com.sky7th.devtimeline.core.domain.linkpost.dto.LinkPostItem;
import com.sky7th.devtimeline.core.domain.linkpost.dto.LinkPostRequestDto;
import com.sky7th.devtimeline.core.domain.linkpost.dto.LinkPostResponseDto;
import com.sky7th.devtimeline.core.domain.linkpost.dto.LinkPostViewResponseDtos;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.post.exception.NotFoundPostException;
import com.sky7th.devtimeline.core.domain.linkpost.service.LinkPostInternalService;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LinkPostService {

    private final LinkPostWebRepository linkPostWebRepository;
    private final CommentWebRepository commentWebRepository;
    private final LinkPostInternalService linkPostInternalService;

    @Transactional(readOnly = true)
    public LinkPostViewResponseDtos findBySearchForm(PostSearchForm postSearchForm, UserContext userContext) {
        List<LinkPostItem> linkPostItems = linkPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(postSearchForm, userContext);
        long recruitPostCounts = linkPostWebRepository.countBySearchForm(postSearchForm, userContext);

        return LinkPostViewResponseDtos.of(linkPostItems, recruitPostCounts);
    }

    @Transactional(readOnly = true)
    public LinkPostViewDetailResponseDto findOne(Long postId, UserContext userContext) {
        LinkPostItem linkPostItem = linkPostWebRepository.findWithLikeCountAndIsLikeByIdAndUserId(postId, userContext)
                .orElseThrow(NotFoundPostException::new);
        linkPostItem.setComments(commentWebRepository.findFromLastCommentIdToLimit(postId, null, 5L));

        return LinkPostViewDetailResponseDto.of(linkPostItem);
    }

    @PreAuthorize("@authService.isLogin(#userContext)")
    public LinkPostResponseDto save(LinkPostRequestDto requestDto, UserContext userContext) {
        return LinkPostResponseDto.of(linkPostInternalService.save(requestDto, userContext));
    }

    @PreAuthorize("@linkPostInternalService.isAuthor(#postId, #userContext)")
    public LinkPostResponseDto update(Long postId, LinkPostRequestDto requestDto, UserContext userContext) throws UnauthorizedException {
        return LinkPostResponseDto.of(linkPostInternalService.update(postId, requestDto));
    }

    @PreAuthorize("@linkPostInternalService.isAuthor(#postId, #userContext)")
    public void delete(Long postId, UserContext userContext) throws UnauthorizedException {
        linkPostInternalService.delete(postId);
    }
}