package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.comment.Comment;
import com.sky7th.devtimeline.core.domain.comment.CommentRepository;
import com.sky7th.devtimeline.web.exception.UnauthorizedException;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void save(CommentDto commentDto, UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        Comment comment = commentDto.toEntity();
        comment.setUser(userPrincipal.toUserForId());
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(Long postId, UserPrincipal userPrincipal) {
        commentRepository.deleteByLinkPostIdAndUserId(postId, userPrincipal.getId());
    }
}
