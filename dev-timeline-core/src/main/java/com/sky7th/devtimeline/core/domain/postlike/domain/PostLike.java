package com.sky7th.devtimeline.core.domain.postlike.domain;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.post.domain.Post;
import com.sky7th.devtimeline.core.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints={
        @UniqueConstraint(
                columnNames={"post_id", "user_id"}
        )
})
public class PostLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_like_post"))
    private Post post;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PostLike(Long postId, Long userId) {
        this.post = new Post(postId);
        this.user = new User(userId);
    }

    public boolean isAuthor(Long userId) {
        return this.user.getId().equals(userId);
    }

    public void setUser(User user) {
        this.user = user;
    }
}