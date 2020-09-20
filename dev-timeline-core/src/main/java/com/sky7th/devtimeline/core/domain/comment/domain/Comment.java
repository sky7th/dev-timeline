package com.sky7th.devtimeline.core.domain.comment.domain;

import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.post.domain.Post;
import com.sky7th.devtimeline.core.domain.user.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_comment_post"))
    private Post post;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public Comment(Long postId, Long userId, String content) {
        this.post = new Post(postId);
        this.user = new User(userId);
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }

    public boolean isAuthor(Long userId) {
        return this.user.getId().equals(userId);
    }

    public void setUser(User user) {
        this.user = user;
    }
}