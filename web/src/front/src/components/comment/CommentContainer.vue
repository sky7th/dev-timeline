<template>
    <div class="comment-container">
        <CommentWrite :postId="postId" @addComment="addComment"/>
        <li
        v-for="(comment) in comments"
            :key="comment.id"
        >
            <Comment :comment="comment" @removeComment="removeComment"/>
        </li>
    </div>
</template>

<script>
import Comment from '@/components/comment/Comment'
import CommentWrite from '@/components/comment/CommentWrite'

export default {
    components: {
        Comment,
        CommentWrite
    },
    props: {
        comments: { type: Array, default: () => []},
        postId: Number
    },
    methods: {
        addComment(comment) {
            this.comments.unshift(comment)
        },
        removeComment(commentId) {
            this.comments = this.comments.filter(comment => comment.id !== commentId);
        }
    }
}
</script>

<style scoped>
</style>