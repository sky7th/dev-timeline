package com.sky7th.devtimeline.core.domain.comment.service;

import com.sky7th.devtimeline.core.domain.comment.domain.Comment;
import com.sky7th.devtimeline.core.domain.comment.domain.CommentRepository;
import com.sky7th.devtimeline.core.domain.comment.dto.CommentRequestDto;
import com.sky7th.devtimeline.core.domain.comment.dto.CommentUpdateRequestDto;
import com.sky7th.devtimeline.core.domain.comment.exception.NotFoundCommentException;
import com.sky7th.devtimeline.core.domain.post.domain.Post;
import com.sky7th.devtimeline.core.domain.post.service.PostInternalService;
import com.sky7th.devtimeline.core.domain.user.domain.User;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import com.sky7th.devtimeline.core.domain.user.exception.MismatchUserException;
import com.sky7th.devtimeline.core.domain.user.service.UserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentInternalService {

    private final CommentRepository commentRepository;
    private final PostInternalService postInternalService;
    private final UserInternalService userInternalService;

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(NotFoundCommentException::new);
    }

    public Comment save(Long postId, CommentRequestDto requestDto, UserContext userContext) {
        Post post = postInternalService.findById(postId);
        post.increaseCommentCount();
        User user = userInternalService.findById(userContext.getId());
        return commentRepository.save(new Comment(post, user, requestDto.getContent()));
    }

    public Comment update(Long commentId, CommentUpdateRequestDto requestDto) {
        Comment comment = findById(commentId);
        comment.update(requestDto.getContent());
        return comment;
    }

    public void delete(Long postId, Long commentId) {
        Post post = postInternalService.findById(postId);
        post.decreaseCommentCount();
        Comment comment = findById(commentId);
        commentRepository.delete(comment);
    }

    public boolean isAuthor(Long commentId, UserContext userContext) {
        Comment comment = findById(commentId);
        if (!comment.isAuthor(userContext.getId())) {
            throw new MismatchUserException();
        }
        return true;
    }
}
