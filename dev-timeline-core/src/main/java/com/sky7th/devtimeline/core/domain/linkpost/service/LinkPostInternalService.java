package com.sky7th.devtimeline.core.domain.linkpost.service;

import com.sky7th.devtimeline.core.domain.linkpost.domain.LinkPost;
import com.sky7th.devtimeline.core.domain.linkpost.domain.LinkPostRepository;
import com.sky7th.devtimeline.core.domain.linkpost.dto.LinkPostRequestDto;
import com.sky7th.devtimeline.core.domain.post.exception.DeletedPostException;
import com.sky7th.devtimeline.core.domain.post.exception.NotFoundPostException;
import com.sky7th.devtimeline.core.domain.tag.dto.TagRequestDto;
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
public class LinkPostInternalService {

    private final LinkPostRepository linkPostRepository;
    private final UserInternalService userInternalService;

    @Transactional(readOnly = true)
    public LinkPost findByPostId(Long postId) {
        LinkPost linkPost = linkPostRepository.findByPost_Id(postId).orElseThrow(NotFoundPostException::new);
        if (linkPost.isDeleted()) {
            throw new DeletedPostException();
        }

        return linkPost;
    }

    public LinkPost save(LinkPostRequestDto requestDto, UserContext userContext) {
        User user = userInternalService.findById(userContext.getId());
        return linkPostRepository.save(LinkPostRequestDto.toEntity(requestDto, user));
    }

    public LinkPost update(Long postId, LinkPostRequestDto requestDto) {
        LinkPost linkPost = findByPostId(postId);
        linkPost.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getLinkUrl());
        linkPost.getPost().updateTags(TagRequestDto.toEntities(requestDto.getTags(), linkPost.getUser()));
        return linkPost;
    }

    public void delete(Long postId) {
        LinkPost linkPost = findByPostId(postId);
        linkPost.delete();
    }

    public boolean isAuthor(Long postId, UserContext userContext) {
        LinkPost linkPost = findByPostId(postId);
        if (!linkPost.isAuthor(userContext.getId())) {
            throw new MismatchUserException();
        }
        return true;
    }
}
