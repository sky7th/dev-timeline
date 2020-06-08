<template>
    <div class="comment-wrapper">
        <img class="img-user" :src="comment.user.imageUrl" alt="">
        <div>
            <div class="header">
                <span class="user-name">{{ comment.user.name }} </span>
                <span>{{ comment.createdDate }}</span>
                <span v-if="isAuthorized" >
                    <span class="between">|</span>
                    <span @click="remove" class="remove-btn">삭제</span>
                </span>
            </div>
            <pre class="content">{{ comment.content }}</pre>
        </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import notification from '../../libs/notification';

export default {
    data: () => ({
        content: '',
        isAuthorized: false
    }),
    created() {
        if (this.currentUser !== null && this.comment.user.id == this.currentUser.id)
        this.isAuthorized = true
    },
    props: {
        comment: { type: Object, default: () => ({
            content: '',
            createdDate: '',
            user: { type: Object, default: () => ({
                name: '',
                email: ''
            })}
        })}
    },
    computed: {
    ...mapGetters(['currentUser', 'post', 'posts'])
    },
    methods: {
        remove() {
            if (!confirm('댓글을 삭제할까요?')) {
                return;
            }
            this.axios.delete(`${process.env.VUE_APP_API}/api/v1/link-posts/comment/${this.comment.id}`)
                .then(() => {
                    notification.success('댓글을 삭제했습니다.');
                    this.handlerRemoveComment(this.comment.id);
                    let post = this.posts.find(v => v.id === this.post.id);
                    post.commentCount -= 1;
                    this.post.commentCount -= 1;
                    
                }).catch(error => {
                    notification.warn(error.response.data.message);
                })
        },
        handlerRemoveComment(commentId) {
            this.$emit('removeComment', commentId)
        }
    }
}
</script>

<style scoped>
.comment-wrapper {
  display: flex;
  margin-bottom: 15px;
}
.comment-wrapper .img-user {
  height: 23px;
  width: 23px;
  border-radius: 5px;
}
.comment-wrapper .header {
    font-size: 14px;
    color: #5a5a5a;
}
.comment-wrapper .user-name {
  margin: 0px 10px 0px 10px;
}
.comment-wrapper .content {
    margin: 8px 0 0 10px;
    font-size: 14px;
}
.between {
    margin: 0 13px 0 14px;
    font-size: 13px;
    color: #5a5a5a;
}
.remove-btn {
    font-size: 13px;
}
.remove-btn:hover {
    cursor: pointer;
    text-decoration: underline;
}
</style>