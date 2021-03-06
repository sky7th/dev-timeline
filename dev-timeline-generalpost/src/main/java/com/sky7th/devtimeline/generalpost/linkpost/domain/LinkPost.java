package com.sky7th.devtimeline.generalpost.linkpost.domain;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.post.post.domain.Post;
import com.sky7th.devtimeline.user.domain.User;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class LinkPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String linkUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "link_type")
    private LinkType linkType;

    @Builder
    public LinkPost(Long id, Post post, User user, String title, String content, String linkUrl) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.title = title;
        this.content = content;
        this.linkUrl = linkUrl;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void update(String title, String content, String linkUrl) {
        this.title = title;
        this.content = content;
        this.linkUrl = linkUrl;
    }

    public boolean isAuthor(Long userId) {
        return this.user.getId().equals(userId);
    }

    public void delete() {
        this.post.delete();
    }

    public boolean isDeleted() {
        return this.post.getDeleteYn();
    }

    public Long getPostId() {
        return this.post.getId();
    }
}