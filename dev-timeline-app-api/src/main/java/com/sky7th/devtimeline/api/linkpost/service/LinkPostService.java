package com.sky7th.devtimeline.api.linkpost.service;

import static com.sky7th.devtimeline.post.config.PagingConstant.COMMENT_PAGE_SIZE;

import com.sky7th.devtimeline.api.comment.repository.CommentWebRepository;
import com.sky7th.devtimeline.api.linkpost.dto.LinkPostItem;
import com.sky7th.devtimeline.api.linkpost.dto.LinkPostViewDetailResponseDto;
import com.sky7th.devtimeline.api.linkpost.dto.LinkPostViewResponseDtos;
import com.sky7th.devtimeline.api.linkpost.repository.LinkPostWebRepository;
import com.sky7th.devtimeline.api.security.exception.UnauthorizedException;
import com.sky7th.devtimeline.generalpost.linkpost.dto.LinkPostRequestDto;
import com.sky7th.devtimeline.generalpost.linkpost.dto.LinkPostResponseDto;
import com.sky7th.devtimeline.generalpost.linkpost.dto.LinkPostSearchForm;
import com.sky7th.devtimeline.generalpost.linkpost.service.LinkPostInternalService;
import com.sky7th.devtimeline.post.post.exception.NotFoundPostException;
import com.sky7th.devtimeline.user.dto.UserContext;
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
    public LinkPostViewResponseDtos findBySearchForm(LinkPostSearchForm postSearchForm) {
        List<LinkPostItem> linkPostItems = linkPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(postSearchForm);

        Long recruitPostCounts = null;
        if (postSearchForm.isFirstLoad()) {
            recruitPostCounts = linkPostWebRepository.countBySearchForm(postSearchForm);
        }

        return LinkPostViewResponseDtos.of(linkPostItems, recruitPostCounts);
    }

    @Transactional(readOnly = true)
    public LinkPostViewDetailResponseDto findOne(Long postId) {
        LinkPostItem linkPostItem = linkPostWebRepository.findWithLikeCountAndIsLikeByIdAndUserId(postId)
                .orElseThrow(NotFoundPostException::new);

        linkPostItem.setComments(commentWebRepository.findFromLastCommentIdToLimit(postId, null, COMMENT_PAGE_SIZE));

        return LinkPostViewDetailResponseDto.of(linkPostItem);
    }

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
