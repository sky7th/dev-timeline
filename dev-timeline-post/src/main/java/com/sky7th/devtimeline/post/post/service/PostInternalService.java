package com.sky7th.devtimeline.post.post.service;

import com.sky7th.devtimeline.post.post.domain.Post;
import com.sky7th.devtimeline.post.post.domain.PostRepository;
import com.sky7th.devtimeline.post.post.exception.NotFoundPostException;
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
