package com.sky7th.devtimeline.core.domain.post.domain;

import com.sky7th.devtimeline.core.domain.comment.domain.Comment;
import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.postlike.domain.PostLike;
import com.sky7th.devtimeline.core.domain.tag.domain.Tag;
import com.sky7th.devtimeline.core.domain.tag.dto.TagRequestDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String crawlId;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @Column(columnDefinition = "integer(11) default 0")
    private Integer likeCount;

    @Column(columnDefinition = "integer(11) default 0")
    private Integer commentCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(Long id, String crawlId, PostType postType, Integer likeCount, Integer commentCount) {
        this.id = id;
        this.crawlId = crawlId;
        this.postType = postType;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public Post(Long id) {
        this.id = id;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.setLinkPost(this);
    }

    public void increaseLikeCount() {
        this.likeCount += 1;
    }

    public void decreaseLikeCount() {
        this.likeCount -= 1;
    }

    public void updateTags(List<TagRequestDto> tagRequestDtos) {
        List<Tag> toBeDeletedTags = getToBeDeletedTags(tagRequestDtos);
        toBeDeletedTags.forEach(this.tags::remove);
        addTags(tagRequestDtos);
    }

    private List<Tag> getToBeDeletedTags(List<TagRequestDto> tagRequestDtos) {
        List<Long> tagItemIds = tagRequestDtos.stream()
            .map(TagRequestDto::getId).collect(Collectors.toList());

        return tags.stream()
            .filter(tag -> !tagItemIds.contains(tag.getId()))
            .collect(Collectors.toList());
    }

    private void addTags(List<TagRequestDto> tagRequestDtos) {
        tagRequestDtos.stream()
            .filter(tagDto -> tagDto.getId() == null)
            .map(TagRequestDto::toEntity)
            .forEach(this::addTag);
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }
}