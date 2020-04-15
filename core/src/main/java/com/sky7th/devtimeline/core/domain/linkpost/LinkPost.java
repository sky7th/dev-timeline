package com.sky7th.devtimeline.core.domain.linkpost;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
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

    @Builder
    public LinkPost(User user, LinkType linkType, List<Tag> tags, String title, String content, String linkUrl) {
        this.linkType = linkType;
        this.user = user;
        this.tags = tags;
        this.title = title;
        this.content = content;
        this.linkUrl = linkUrl;
    }
}