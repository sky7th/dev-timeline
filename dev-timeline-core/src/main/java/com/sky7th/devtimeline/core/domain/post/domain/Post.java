package com.sky7th.devtimeline.core.domain.post.domain;

import com.sky7th.devtimeline.core.domain.comment.domain.Comment;
import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.postlike.domain.PostLike;
import com.sky7th.devtimeline.core.domain.tag.domain.Tag;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
    private Long likeCount;

    @Column(columnDefinition = "integer(11) default 0")
    private Long commentCount;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleteYn = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private Set<Tag> tags = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public Post(PostType postType, List<Tag> tags) {
        this(null, null, postType, 0L, 0L, tags);
    }

    @Builder
    public Post(Long id, String crawlId, PostType postType, Long likeCount, Long commentCount, List<Tag> tags) {
        this.id = id;
        this.crawlId = crawlId;
        this.postType = postType;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        tags.forEach(this::addTag);
    }

    public Post(Long id) {
        this.id = id;
    }

    public void addTag(Tag tag) {
        tag.setPost(this);
        this.tags.add(tag);
    }

    public void increaseLikeCount() {
        this.likeCount += 1;
    }

    public void decreaseLikeCount() {
        this.likeCount -= 1;
    }

    public void increaseCommentCount() {
        this.commentCount += 1;
    }

    public void decreaseCommentCount() {
        this.commentCount -= 1;
    }

    public void updateTags(List<Tag> requestTags) {
        deleteTagsNotContainedIn(requestTags);
        requestTags.forEach(this::addTag);
    }

    private void deleteTagsNotContainedIn(List<Tag> requestTags) {
        List<Tag> toBeDeletedTags = tags.stream()
            .filter(tag -> !requestTags.contains(tag))
            .collect(Collectors.toList());
        toBeDeletedTags.forEach(this.tags::remove);
    }

    public void delete() {
        this.deleteYn = true;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }
}