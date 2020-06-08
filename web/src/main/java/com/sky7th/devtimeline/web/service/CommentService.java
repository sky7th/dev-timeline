package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.comment.Comment;
import com.sky7th.devtimeline.core.domain.comment.CommentRepository;
import com.sky7th.devtimeline.core.domain.user.User;
import com.sky7th.devtimeline.core.domain.user.UserRepository;
import com.sky7th.devtimeline.web.exception.UnauthorizedException;
import com.sky7th.devtimeline.web.repository.comment.CommentWebRepository;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentWebRepository commentWebRepository;
    private final UserRepository userRepository;

    public List<CommentDto> findByOffsetAndLimit(Long postId, Long lastCommentId, Long limit) {
        List<Comment> comments = commentWebRepository.findFromLastCommentIdToLimit(postId, lastCommentId, limit);

        return comments.stream().map(CommentDto::new).collect(Collectors.toList());
    }

    @Transactional
    public CommentDto save(CommentDto commentDto, UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            throw new UnauthorizedException("로그인 후 이용 가능합니다.");
        }
        User user = userRepository.findById(userPrincipal.getId()).orElse(null);
        Comment comment = commentDto.toEntity();
        comment.setUser(user);

        return new CommentDto(commentWebRepository.save(comment));
    }

    @Transactional
    public void delete(Long id, UserPrincipal userPrincipal) {
        Comment comment = commentWebRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 comment는 존재하지 않습니다. id=" + id));

        if (!comment.getUser().getId().equals(userPrincipal.getId())) {
            throw new UnauthorizedException("작성자 본인만 수정이 가능합니다.");
        }
        commentWebRepository.deleteById(id);
    }
}
