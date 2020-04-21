package com.sky7th.devtimeline.core.domain.post.linkpost;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.like.PostLike;
import com.sky7th.devtimeline.core.domain.tag.Tag;
import com.sky7th.devtimeline.core.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class LinkPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "link_type")
    private LinkType linkType;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linkPost")
    private List<Tag> tags = new ArrayList<>();

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String linkUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linkPost")
    private List<PostLike> postLikes = new ArrayList<>();

    @Builder
    public LinkPost(Long id, User user, LinkType linkType, String title, String content, String linkUrl) {
        this.id = id;
        this.linkType = linkType;
        this.user = user;
        this.title = title;
        this.content = content;
        this.linkUrl = linkUrl;
    }

    public void addTags(Tag tag) {
        this.tags.add(tag);
        tag.setLinkPost(this);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void update(String title, String content, String linkUrl) {
        this.title = title;
        this.content = content;
        this.linkUrl = linkUrl;
    }

}