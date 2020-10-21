package com.sky7th.devtimeline.api.comment.service;

import com.sky7th.devtimeline.api.comment.repository.CommentWebRepository;
import com.sky7th.devtimeline.post.comment.dto.CommentRequestDto;
import com.sky7th.devtimeline.post.comment.dto.CommentResponseDto;
import com.sky7th.devtimeline.post.comment.dto.CommentUpdateRequestDto;
import com.sky7th.devtimeline.post.comment.service.CommentInternalService;
import com.sky7th.devtimeline.user.dto.UserContext;
import java.util.List;
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
    public List<CommentResponseDto> findByOffsetAndLimit(Long postId, Long lastCommentId, Long limit) {
        return CommentResponseDto.of(commentWebRepository.findFromLastCommentIdToLimit(postId, lastCommentId, limit));
    }

    public CommentResponseDto save(Long postId, CommentRequestDto requestDto, UserContext userContext) {
        return CommentResponseDto.of(commentInternalService.save(postId, requestDto, userContext));
    }

    @PreAuthorize("@commentInternalService.isAuthor(#commentId, #userContext)")
    public CommentResponseDto update(Long commentId, CommentUpdateRequestDto requestDto, UserContext userContext) {
        return CommentResponseDto.of(commentInternalService.update(commentId, requestDto));
    }

    @PreAuthorize("@commentInternalService.isAuthor(#commentId, #userContext)")
    public void delete(Long postId, Long commentId, UserContext userContext) {
        commentInternalService.delete(postId, commentId);
    }
}
