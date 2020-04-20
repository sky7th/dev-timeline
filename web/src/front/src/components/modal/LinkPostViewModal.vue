<template>
  <div class="link-post-modal">
    <div class="post-container">
      <div class="middle">
        <div class="tags">
          <span class="tag" v-for="({ id, name }) in linkPost.tags" :key="id">#{{ name }}</span>
        </div>
        <div class="title">{{ linkPost.title }}</div>
        <div class="middle-bottom">
          <div class="author-container">
            <img class="img-author" :src="linkPost.user.imageUrl" alt="">
            <div class="author">{{ linkPost.user.name }}</div>
          </div>
          <div class="between">|</div>
          <div class="date">{{ linkPost.createdDate }}</div>
        </div>
      </div>
    </div>
    <div class="wrapper link-wrapper">
      <!-- <div class="description">link</div> -->
      <a :href="linkPost.linkUrl" class="link" target="_blank">{{ linkPost.linkUrl }}</a>
    </div>
    <div class="wrapper content-wrapper">
      <div class="description">description</div>
      <div class="content">{{ linkPost.content }}</div>
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

export default {
  data: () => ({
    linkPost: { 
      title: '',
      linkUrl: '',
      content: '',
      tags: [],
      user: {}
    },
    isPossibleUpdate: false
  }),
  created() {
    var postId = localStorage.getItem('postId');
    this.axios.get(`${process.env.VUE_APP_API}/api/v1/link-posts/${postId}`)
      .then(response => {
        this.linkPost = response.data.data;
        if (this.currentUser !== null && this.linkPost.user.id == this.currentUser.id)
          this.isPossibleUpdate = true
      }).catch(() => {
        notification.warn('글을 불러오지 못했습니다.');
      })
  },
  components: {
    BlueButton
  },  
  computed: {
    ...mapGetters(['currentUser'])
  },
  methods: {
    ...mapActions(['offModalState', 'resetOffset', 'updatePosts', 'updatePostState']),
    update() {
      localStorage.setItem('post', JSON.stringify(this.linkPost));
      this.updatePostState(Constant.UPDATE);
    }
  }
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

</style>