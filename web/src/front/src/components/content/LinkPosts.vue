<template>
  <div class="link-posts">
    <FixedTagBar/>
    <CountBar style="margin-bottom: 11px;"/>
    <ul>
      <li
        v-for="({
          id, 
          title,
          linkUrl,
          tags,
          user,
          createdDate,
          likeCount,
          like}) in posts"
        :key="id"
        @click="handlerOnModalState(id)"
      >
        <a class="post-container" target="_blank">
          <div class="middle">
            <div class="tags">
              <span class="tag" v-for="({ id, name }) in tags" :key="id">#{{ name }}</span>
            </div>
            <div class="title">{{ title }}</div>
            <div class="middle-bottom">
              <div class="author-container">
                <img class="img-author" :src="user.imageUrl" alt="">
                <div class="author">{{ user.name }}</div>
              </div>
              <div class="between">|</div>
              <div class="date">{{ createdDate }}</div>
            </div>
          </div>
        </a>
        <div class="post-container-btn">
          <div class="like">
            <div class="btn-like">
              <font-awesome-icon v-if="like" :icon="['fas', 'heart']" @click="cancelLike(id)" class="btn-like-full"/>
              <font-awesome-icon v-else :icon="['far', 'heart']" @click="doLike(id)"/>
            </div>
            <span class="like-count">{{ likeCount }}</span>
          </div>
        </div>
      </li>
    </ul>
    <infinite-loading class="infinite-message" @infinite="handlerInfinite" spinner="waveDots">
      <div slot="no-more">
        <ScrollUp/>
      </div>
      <div slot="no-results">링크정보를 가져오지 못했어요 ..ㅠ.ㅠ</div>
    </infinite-loading>
  </div>
</template>

<script>
import CountBar from '@/components/search/CountBar';
import FixedTagBar from '@/components/search/FixedTagBar';
import ScrollUp from '@/components/common/ScrollUp'
import { mapGetters, mapActions } from "vuex";
import InfiniteLoading from 'vue-infinite-loading';
import Constant from '@/constant/Constant';
import { faHeart as fasHeart } from '@fortawesome/free-solid-svg-icons' 
import { faHeart as farHeart } from '@fortawesome/free-regular-svg-icons' 
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core' 
import notification from '../../libs/notification';

faLibrary.add(fasHeart, farHeart) 

export default {
  components: {
    InfiniteLoading,
    CountBar,
    FixedTagBar,
    ScrollUp
  },
  computed: {
    ...mapGetters(['posts', 'currentUser'])
  },
  methods: {
    ...mapActions(['insertPosts', 'onModalState', 'resetOffset', 'updatePosts', 'updatePostState', 'updatePost']),
    handlerInfinite($state) {
      this.insertPosts({ infiniteState: $state })
    },
    handlerOnModalState(postId) {
      this.axios.get(`${process.env.VUE_APP_API}/api/v1/link-posts/${postId}`)
        .then(response => {
          var post = this.posts.find(v => v.id === postId);
          this.updatePost(post)
          this.post = response.data.data;
          
          this.updatePostState(Constant.READ);
          this.onModalState();
        }).catch(() => {
          notification.warn('글을 불러오지 못했습니다.');
        })
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
  }
}
</script>

<style scoped>
.link-posts {
  animation: fadeIn 0.3s ease-in-out;
}
.link-posts .post-container {
  display: flex; 
  width: 100%;
  cursor: pointer;
  padding: 10px 23px 8px 16px;
}
.link-posts .post-container:hover {
  transition: opacity .2s;
  opacity: 0.7;
}
.link-posts > ul {
  text-align: center;
  padding: 3px 20px;
}
.link-posts > ul > li {
  position: relative;
  width: 330px;
  display: inline-flex;
  justify-content: flex-end;
  margin: 15px;
  background-color: white;
  margin-bottom: 15px;
  border-radius: 10px;
  -webkit-animation: fadeIn 0.3s ease-in-out;
  animation: fadeIn 0.3s ease-in-out;
  box-shadow: 0 1px 2px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
}
.link-posts > ul > li:hover {
  box-shadow: 0 1px 4px rgba(27,31,35,.3);
  transition: background-color .2s;
  background-color: #f4f4f4;
}
.author-container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  margin-right: 0px;
  max-width: 100px;
}
.img-author {
  height: 21px;
  width: 21px;
  border-radius: 5px;
}
.author {
  margin-left: 10px;
  font-size: 13px;
  color: #5a5a5a;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.middle {
  flex: 3;
  justify-content: center;
  flex-direction: column;
  display: flex;
  width: 100%;
}
.title {
  margin-bottom: 13px;
  font-size: 18px;
  font-weight: bold;
  text-align: end;
  line-height: 140%;
  
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  
  white-space: normal;
  word-wrap: break-word; 
  display: -webkit-box; 
  -webkit-line-clamp: 3; 
  -webkit-box-orient: vertical;

}
.middle-bottom {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.between {
  margin: 0 14px 0 14px;
  font-size: 13px;
  color: #5a5a5a;
}
.date {
  font-size: 13px;
  color: #5a5a5a;
}
.infinite-message {
  font-size: 14px;
  margin: 80px 0 30px 0;
}
.infinite-message div {

}
.infinite-message a {
  cursor: pointer;
  padding: 7px 8px;
  margin: 13px 7px;
}
.infinite-message a:hover {
  background-color: #e4e4e4;
}
.tags {
  display: flex;
  margin-bottom: 12px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 250px;
}
.tag {
  font-size: 12px;
  color: #6a6a6a;
  white-space: nowrap;
  margin-right: 10px;
}
.top {
  margin-bottom: 30px;
}
.post-container-btn {
  position: absolute;
  display: flex;
  bottom: 10px;
  left: 15px;
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