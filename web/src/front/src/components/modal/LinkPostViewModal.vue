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
          <div class="date">{{ post.createdDate }}</div>
        </div>
      </div>
      <div class="post-container-btn">
        <div class="like">
          <div class="btn-like">
            <font-awesome-icon v-if="post.like" :icon="['fas', 'heart']" @click="cancelLike(post.id)" class="btn-like-full"/>
            <font-awesome-icon v-else :icon="['far', 'heart']" @click="doLike(post.id)"/>
          </div>
          <span class="like-count">{{ post.likeCount }}</span>
        </div>
      </div>
    </div>
    <div class="wrapper link-wrapper">
      <!-- <div class="description">link</div> -->
      <a :href="post.linkUrl" class="link" target="_blank">{{ post.linkUrl }}</a>
    </div>
    <div class="wrapper content-wrapper">
      <div class="description">description</div>
      <div class="content">{{ post.content }}</div>
    </div>
    <div v-if="isPossibleUpdate" class="bottom-wrapper">
      <BlueButton class="btn-submit" type="submit" :name="'수정'"
        @click="update"/>
    </div>
    <notifications group="notify" position="bottom center"/>
  </div>
</template>

<script>
import BlueButton from '@/components/common/button/BlueButton'
import { mapGetters, mapActions } from "vuex";
import notification from '../../libs/notification';
import Constant from '@/constant/Constant';
import { faHeart as fasHeart } from '@fortawesome/free-solid-svg-icons' 
import { faHeart as farHeart } from '@fortawesome/free-regular-svg-icons' 
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core' 

faLibrary.add(fasHeart, farHeart) 

export default {
  data: () => ({
    linkPost: { 
      title: '',
      linkUrl: '',
      content: '',
      tags: [],
      like: false,
      likeCount: 0,
      user: {}
    },
    isPossibleUpdate: false
  }),
  created() {
    if (this.currentUser !== null && this.post.user.id == this.currentUser.id)
      this.isPossibleUpdate = true
  },
  components: {
    BlueButton
  },  
  computed: {
    ...mapGetters(['currentUser', 'posts', 'post'])
  },
  methods: {
    ...mapActions(['offModalState', 'resetOffset', 'updatePosts', 'updatePostState', 'updatePost']),
    update() {
      this.updatePost(this.linkPost)
      this.updatePostState(Constant.UPDATE);
    },
    doLike(linkPostId) {
      event.stopPropagation();
      var data = {
        postType: 'LINK_POST',
        linkPost: { id: linkPostId }
      };
      this.axios.post(`${process.env.VUE_APP_API}/api/v1/like/link-posts`, data)
      .then(() => {
        var post = this.posts.filter(v => v.id === linkPostId)[0];
        post.like = true;
        post.likeCount += 1;
      }).catch(() => {
        notification.warn('문제가 생겨 좋아요를 하지 못했습니다.')
      })
    },
    cancelLike(linkPostId) {
      event.stopPropagation();
      this.axios.delete(`${process.env.VUE_APP_API}/api/v1/like/link-posts/${linkPostId}`)
      .then(() => {
        var post = this.posts.filter(v => v.id === linkPostId)[0];
        post.like = false;
        post.likeCount -= 1;
      }).catch(() => {
        notification.warn('문제가 생겨 좋아요를 취소하지 못했습니다.')
      })
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
  margin-bottom: 6px;
  font-size: 15px;
  color: #6a6a6a;
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

</style>