package com.sky7th.devtimeline.api.linkpost.dto;

import com.sky7th.devtimeline.generalpost.linkpost.domain.LinkPost;
import com.sky7th.devtimeline.post.comment.domain.Comment;
import com.sky7th.devtimeline.user.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LinkPostItem {

    private LinkPost linkPost;
    private User user;
    private Boolean isLike;
    private List<Comment> comments;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
