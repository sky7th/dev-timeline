package com.sky7th.devtimeline.core.domain.tag;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_post_id", foreignKey = @ForeignKey(name = "fk_tag_link_post"))
    private LinkPost linkPost;

    private String name;

    @Builder
    public Tag(Long id, LinkPost linkPost, String name) {
        this.id = id;
        this.linkPost = linkPost;
        this.name = name;
    }

    public void setLinkPost(LinkPost linkPost) {
        this.linkPost = linkPost;
    }

}