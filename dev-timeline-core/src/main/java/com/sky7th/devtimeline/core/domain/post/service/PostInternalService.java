package com.sky7th.devtimeline.core.domain.post.service;

import com.sky7th.devtimeline.core.domain.post.domain.Post;
import com.sky7th.devtimeline.core.domain.post.domain.PostRepository;
import com.sky7th.devtimeline.core.domain.post.exception.NotFoundPostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostInternalService {

  private final PostRepository postRepository;

  @Transactional(readOnly = true)
  public Post findById(Long postId) {
    return postRepository.findById(postId).orElseThrow(NotFoundPostException::new);
  }
}
