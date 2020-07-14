<template>
    <div class="comment-container">
        <CommentWrite :postId="postId" @addComment="addComment"/>
        <ul>
            <li
            v-for="(comment) in comments"
                :key="comment.id"
            >
                <Comment :comment="comment" @removeComment="removeComment"/>
            </li>
        </ul>
        <div class="loading-wrapper">
            <img src="../../assets/images/loading.gif" class="loading"/>
        </div>
    </div>
</template>

<script>
import Comment from '@/components/comment/Comment'
import CommentWrite from '@/components/comment/CommentWrite'
import { scrollFetch } from '@/utils/scrollFetch.js';

export default {
    mounted() {
        scrollFetch(() => this.addComments(), document.querySelector('.modal .wrapper'));
    },
    data: () => ({
        lastCommentId: 0
    }),
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
            const idx = this.comments.findIndex(comment => comment.id === commentId);            
            if (idx > -1) 
                this.comments.splice(idx, 1)
        },
        addComments() {
            let loadingElement = document.querySelector('.loading');
            loadingElement.style.display = 'block';
            loadingElement.style.visibility = 'visible';

            let lastCommentId = this.comments.length === 0 ? 1 : this.comments[this.comments.length-1].id;
            this.axios.get(`${process.env.VUE_APP_API}/api/v1/link-posts/comment?postId=${this.postId}&lastCommentId=${lastCommentId}&limit=${5}`)
                .then(({ data }) => {
                    if (data.data.length) {
                        this.comments = [...this.comments, ...data.data];
                    } else {
                        loadingElement.style.visibility = 'hidden';
                    }
                }).catch(error => {
                    console.log(error);
                    loadingElement.style.visibility = 'hidden';
                })
        }
    }
}
</script>

<style scoped>
.loading-wrapper {
    display: flex;
    flex-direction: row;
    justify-content: center;
    text-align: center;
}
.loading {
    display: none;
    height: 50px;
    margin-top: -10px;
}
li + li {
  margin-top: 13px;
  padding-top: 13px;
  border-top: 1px dashed #ddd;
}
</style>