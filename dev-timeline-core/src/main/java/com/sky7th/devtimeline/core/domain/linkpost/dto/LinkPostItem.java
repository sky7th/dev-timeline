package com.sky7th.devtimeline.core.domain.linkpost.dto;

import com.sky7th.devtimeline.core.domain.comment.domain.Comment;
import com.sky7th.devtimeline.core.domain.linkpost.domain.LinkPost;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LinkPostItem {

    private LinkPost linkPost;
    private Long likeCount;
    private Boolean isLike;
    private Long commentCount;
    private List<Comment> comments;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
