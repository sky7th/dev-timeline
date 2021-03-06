<template>
  <div class="link-post-modal">
    <div class="post-container">
      <div class="middle">
        <div class="tags">
          <span class="tag" v-for="({ id, name }) in post.tags" :key="id">#{{ name }}</span>
        </div>
        <div class="title">{{ post.title }}</div>
        <div class="middle-bottom">
          <div class="author-container">
            <img class="img-author" :src="post.user.imageUrl" alt="">
            <div class="author">{{ post.user.name }}</div>
          </div>
          <div class="between">|</div>
          <div class="date">{{ getDateTime(post.createdDate) }}</div>
        </div>
      </div>
      <Like :isLike="post.like" :postId="post.id" :likeCount="post.likeCount" class="like-wrapper"/>
      <div class="post-container-btn comment-icon">
          <div class="like">
            <div class="btn-comment">
              <font-awesome-icon :icon="['far', 'comment']"/>
            </div>
            <span class="like-count">{{ post.commentCount }}</span>
          </div>
        </div>
    </div>
    <div class="wrapper link-wrapper">
      <!-- <div class="description">link</div> -->
      <a :href="post.linkUrl" class="link" target="_blank">{{ post.linkUrl }}</a>
    </div>
    <div class="wrapper content-wrapper">
      <div class="description"></div>
      <pre class="content">{{ post.content }}</pre>
    </div>
    <div v-if="isPossibleUpdate" class="bottom-wrapper">
      <BlueButton class="btn-submit" type="submit" :name="'수정'" style="margin-right: 5px;"
        @click="update"/>
      <BlueButton class="btn-submit" type="submit" :name="'삭제'"
        @click="remove"/>
    </div>
    <CommentContainer :comments="post.comments" :postId="post.id"/>
    <notifications group="notify" position="bottom center"/>
  </div>
</template>

<script>
import BlueButton from '@/components/common/button/BlueButton'
import CommentContainer from '@/components/comment/CommentContainer'
import Like from '@/components/like/Like'
import { mapGetters, mapActions } from "vuex";
import notification from '../../libs/notification';
import Constant from '@/constant/Constant';
import { faHeart as fasHeart } from '@fortawesome/free-solid-svg-icons' 
import { faHeart as farHeart } from '@fortawesome/free-regular-svg-icons' 
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core' 

faLibrary.add(fasHeart, farHeart) 

export default {
  data: () => ({
    isPossibleUpdate: false
  }),
  created() {
    if (this.currentUser !== null && this.post.user.id == this.currentUser.id)
      this.isPossibleUpdate = true
  },
  components: {
    BlueButton,
    CommentContainer,
    Like
  },  
  computed: {
    ...mapGetters(['currentUser', 'posts', 'post'])
  },
  methods: {
    ...mapActions(['offModalState', 'resetOffset', 'updatePosts', 'removePost', 'updatePostState', 'updatePost', 'updateIsLoadingContent']),
    update() {
      this.updatePostState(Constant.UPDATE);
    },
    remove() {
        if (!confirm('게시글을 삭제할까요?')) {
            return;
        }
        this.updateIsLoadingContent(true);
        this.axios.delete(`${process.env.VUE_APP_API}/api/v1/link-posts/${this.post.id}`)
            .then(() => {
                notification.success('게시글을 삭제했습니다.');
                this.handlerClosePopup();
                this.removePost(this.post.id);
            }).catch(error => {
                notification.warn(error.response.data.message);
            }).finally(() => this.updateIsLoadingContent(false))
    },
    handlerClosePopup() {
      this.$emit('closePopup');
    },
    getDateTime(time) {
      time = time.replaceAll('-', '.')
      return time.substring(0, 16)
    }
  },
}
</script>

<style scoped>
.link-post-modal {
  padding: 0px 10px;
  overflow-y: scroll;
  animation: fadeIn 0.3s ease-in-out;
}
.post-container {
  display: flex; 
  width: 100%;
  margin-bottom: 30px;
  position: relative;
}
.post-container .middle {
  flex: 3;
  justify-content: center;
  flex-direction: column;
  display: flex;
  width: 100%;
  padding-right: 8px;
}
.post-container .middle .tags {
  margin-bottom: 18px;
}
.post-container .middle .tags .tag {
  font-size: 12px;
  color: #6a6a6a;
  white-space: nowrap;
  margin-right: 10px;
  display: inline-block;
}
.post-container .middle .title {
  margin-bottom: 18px;
  font-size: 18px;
  font-weight: bold;
  text-align: end;
  max-height: 200px;
  line-height: 140%;
  word-break: break-all;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 8;
  -webkit-box-orient: vertical;
}
.post-container .middle .middle-bottom {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.post-container .middle .middle-bottom .author-container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  margin-right: 0px;
}
.post-container .middle .middle-bottom .author-container .img-author {
  height: 23px;
  width: 23px;
  border-radius: 5px;
}
.post-container .middle .middle-bottom .author-container .author {
  margin-left: 10px;
  font-size: 14px;
  color: #5a5a5a;
}
.post-container .middle .middle-bottom .between {
  margin: 0 18px 0 18px;
  font-size: 14px;
  color: #5a5a5a;
}
.post-container .middle .middle-bottom .date {
  font-size: 14px;
  color: #5a5a5a;
}
.description {
  /* margin-bottom: 6px;
  font-size: 15px;
  color: #6a6a6a; */
  border-top: 1px solid #cccccc;
}
.wrapper {
  margin-bottom: 25px;
}
.link {
  font-size: 14px;
  line-height: 1.2;
  display: inline-flex;
  align-items: center;
  padding: 6px 10px;
  border: 0;
  border-radius: 4px;
  color: #fff;
  overflow: hidden;
  max-width: 100%;
  background-color: #2d92f1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  display: inline-block;
}
.link:hover {
  background-color: #65abec;
}
.link-wrapper {
  text-align: right;
  width: 100%;
}
.content {
  font-size: 15px;
  color: #4a4a4a;
  height: 100%;
  line-height: 1.35;
  white-space: pre-wrap;
  line-break: anywhere;
}
.bottom-wrapper {
  text-align: right;
}
.content-wrapper {
  flex: 1;
}
.post-container-btn {
  position: absolute;
  display: flex;
  bottom: 0px;
  left: 0px;
  color: #7a7a7a;
  font-size: 14px;
}
.like-count {
  margin-left: 4px;
  font-size: 13px;
}
.btn-like {
  cursor: pointer;
  display: inline-block;
}
.like {
}
.btn-like:hover {
  font-size: 22px;
  bottom: 5px;
  transition: all .1s ease-in;
}
.btn-like-full {
  color: #ff5784
}
.comment-icon {
    margin-left: 40px;
}
.btn-comment {
    display: inline-block;
}
.like-wrapper {
  bottom: 0;
  left: 0;
}
@media screen and (max-width: 480px) {
  .post-container .middle .middle-bottom {
    align-items: flex-end;
    flex-direction: column;
  }
  .between {
    visibility: hidden;
    height: 13px;
  }
  .comment-icon {
      margin-left: 50px;
  }
  .post-container-btn {
    font-size: 17px;
  }
}
</style>