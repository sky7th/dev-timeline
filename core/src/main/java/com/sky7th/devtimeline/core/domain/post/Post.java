package com.sky7th.devtimeline.core.domain.post;

import com.sky7th.devtimeline.core.domain.comment.Comment;
import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.like.PostLike;
import com.sky7th.devtimeline.core.domain.tag.Tag;
import com.sky7th.devtimeline.core.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    public Post(Long id, String crawlId, PostType postType, User user, Integer likeCount, Integer commentCount) {
        this.id = id;
        this.crawlId = crawlId;
        this.postType = postType;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public void addTags(Tag tag) {
        this.tags.add(tag);
        tag.setLinkPost(this);
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }
}