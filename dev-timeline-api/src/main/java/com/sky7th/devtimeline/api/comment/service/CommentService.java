package com.sky7th.devtimeline.api.comment.service;

import com.sky7th.devtimeline.api.comment.repository.CommentWebRepository;
import com.sky7th.devtimeline.core.domain.comment.dto.CommentRequestDto;
import com.sky7th.devtimeline.core.domain.comment.dto.CommentResponseDto;
import com.sky7th.devtimeline.core.domain.comment.dto.CommentResponseDtos;
import com.sky7th.devtimeline.core.domain.comment.dto.CommentUpdateRequestDto;
import com.sky7th.devtimeline.core.domain.comment.service.CommentInternalService;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentWebRepository commentWebRepository;
    private final CommentInternalService commentInternalService;

    @Transactional(readOnly = true)
    public CommentResponseDtos findByOffsetAndLimit(Long postId, Long lastCommentId, Long limit) {
        return CommentResponseDtos.of(commentWebRepository.findFromLastCommentIdToLimit(postId, lastCommentId, limit));
    }

    @PreAuthorize("@authService.isLogin(#userContext)")
    public CommentResponseDto save(Long postId, CommentRequestDto requestDto, UserContext userContext) {
        return CommentResponseDto.of(commentInternalService.save(postId, requestDto, userContext));
    }

    @PreAuthorize("@commentInternalService.isAuthor(#commentId, #userContext)")
    public CommentResponseDto update(Long commentId, CommentUpdateRequestDto requestDto, UserContext userContext) {
        return CommentResponseDto.of(commentInternalService.update(commentId, requestDto));
    }

    @PreAuthorize("@commentInternalService.isAuthor(#commentId, #userContext)")
    public void delete(Long commentId, UserContext userContext) {
        commentInternalService.delete(commentId);
    }
}
