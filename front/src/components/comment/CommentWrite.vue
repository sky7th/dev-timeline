<template>
    <div class="comment-wrapper">
        <TextareaForm class="comment-input" name="content" placeholder="댓글 내용을 입력해주세요..." 
            v-model="content"
        />
        <BlueButton class="btn-submit" type="submit" :name="'댓글 달기'"
        @click="submit"/>
    </div>
</template>

<script>
import TextareaForm from '@/components/common/TextareaForm'
import BlueButton from '@/components/common/button/BlueButton'
import notification from '../../libs/notification';
import { mapGetters } from "vuex";

export default {
    data: () => ({
        content: ''
    }),
    props: ['postId'],
    components:{
        TextareaForm,
        BlueButton
    },
    computed: {
    ...mapGetters(['currentUser', 'post', 'posts'])
    },
    methods: {
        submit() {
            if (this.content == '') {
                notification.warn('댓글 내용을 입력해주세요.');
                document.querySelector('.title-input').focus();
                return;
            }
            let formData = {
                postType: 'LINK_POST',
                postId: this.postId,
                content: this.content
            }
            this.axios.post(`${process.env.VUE_APP_API}/api/v1/link-posts/comment`, formData)
                .then(response => {
                    notification.success('댓글을 작성했습니다.');
                    this.handlerAddComment(response.data.data);
                    let post = this.posts.find(v => v.id === this.post.id);
                    post.commentCount += 1;
                    this.post.commentCount += 1;
                    this.content = '';

                }).catch(error => {
                    notification.warn(error.response.data.message);
                })
        },
        handlerAddComment(comment) {
            this.$emit('addComment', comment)
        }
    }
}
</script>

<style scoped>
.comment-wrapper {
    text-align: right;
}
.comment-input {
    margin: 15px 0 5px 0;
}
.btn-submit {
    margin-bottom: 15px;
}
</style>