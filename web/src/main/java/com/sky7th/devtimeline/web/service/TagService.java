package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.post.Post;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.core.domain.tag.Tag;
import com.sky7th.devtimeline.core.domain.tag.TagRepository;
import com.sky7th.devtimeline.web.service.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public void updateTags(Post post, List<TagDto> tagDtos) {
        List<Tag> toBeDeletedTags = getToBeDeletedTags(post, tagDtos);
        deleteTags(post, toBeDeletedTags);
        addTags(post, tagDtos);
    }

    private List<Tag> getToBeDeletedTags(Post post, List<TagDto> tagDtos) {
        List<Long> tagItemIds = tagDtos.stream()
                .map(TagDto::getId).collect(Collectors.toList());

        return post.getTags().stream()
                .filter(tag -> !tagItemIds.contains(tag.getId()))
                .collect(Collectors.toList());
    }

    private void deleteTags(Post post, List<Tag> toBeDeletedTags) {
        toBeDeletedTags.forEach(deleteTag -> {
            tagRepository.delete(deleteTag);
            post.getTags().remove(deleteTag);
        });
    }

    private void addTags(Post post, List<TagDto> tagDtos) {
        tagDtos.stream()
                .filter(tagDto -> tagDto.getId() == null)
                .map(TagDto::toTag)
                .forEach(post::addTags);
    }

}
