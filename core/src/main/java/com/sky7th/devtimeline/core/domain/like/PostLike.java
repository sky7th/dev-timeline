package com.sky7th.devtimeline.core.domain.like;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.post.PostType;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.core.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints={
        @UniqueConstraint(
                columnNames={"link_post_id", "user_id"}
        )
})
public class PostLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @ManyToOne
    @JoinColumn(name = "link_post_id", foreignKey = @ForeignKey(name = "fk_like_link_post"))
    private LinkPost linkPost;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PostLike(PostType postType, LinkPost linkPost, User user) {
        this.postType = postType;
        this.linkPost = linkPost;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}