package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.core.domain.tag.Tag;
import com.sky7th.devtimeline.core.domain.tag.TagRepository;
import com.sky7th.devtimeline.web.service.dto.TagItem;
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
    public void updateTags(LinkPost linkPost, List<TagItem> tagItems) {
        List<Tag> toBeDeletedTags = getToBeDeletedTags(linkPost, tagItems);
        deleteTags(linkPost, toBeDeletedTags);
        addTags(linkPost, tagItems);
    }

    private List<Tag> getToBeDeletedTags(LinkPost linkPost, List<TagItem> tagItems) {
        List<Long> tagItemIds = tagItems.stream()
                .map(TagItem::getId).collect(Collectors.toList());

        return linkPost.getTags().stream()
                .filter(tag -> !tagItemIds.contains(tag.getId()))
                .collect(Collectors.toList());
    }

    private void deleteTags(LinkPost linkPost, List<Tag> toBeDeletedTags) {
        toBeDeletedTags.forEach(deleteTag -> {
            tagRepository.delete(deleteTag);
            linkPost.getTags().remove(deleteTag);
        });
    }

    private void addTags(LinkPost linkPost, List<TagItem> tagItems) {
        tagItems.stream()
                .filter(tagItem -> tagItem.getId() == null)
                .map(TagItem::toTag)
                .forEach(linkPost::addTags);
    }

}
