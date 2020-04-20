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
          createdDate}) in posts"
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
      </li>
    </ul>
    <infinite-loading class="infinite-message" @infinite="handlerInfinite" spinner="waveDots">
      <div slot="no-more">
        <div>마지막 부분이에요. 위로 올라가시겠어요?</div> 
        <br/>
        <a href="javascript:scroll(0,0)">네</a>
        <a href="javascript:alert('o=| O ____ O |=o')">아니요</a>
      </div>
      <div slot="no-results">링크정보를 가져오지 못했어요 ..ㅠ.ㅠ</div>
    </infinite-loading>
  </div>
</template>

<script>
import CountBar from '@/components/search/CountBar';
import FixedTagBar from '@/components/search/FixedTagBar';
import { mapGetters, mapActions } from "vuex";
import InfiniteLoading from 'vue-infinite-loading';
import Constant from '@/constant/Constant';

export default {
  components: {
    InfiniteLoading,
    CountBar,
    FixedTagBar
  },
  computed: {
    ...mapGetters(['posts', 'currentUser'])
  },
  methods: {
    ...mapActions(['insertPosts', 'onModalState', 'resetOffset', 'updatePosts', 'updatePostState']),
    handlerInfinite($state) {
      this.insertPosts({ infiniteState: $state })
    },
    handlerOnModalState(id) {
      localStorage.setItem('postId', id);
      this.updatePostState(Constant.READ);
      this.onModalState();
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
}
.link-posts > ul {
  text-align: center;
  padding: 3px 20px;
}
.link-posts > ul > li {
  width: 300px;
  display: inline-flex;
  justify-content: flex-end;
  margin: 15px;
  background-color: white;
  padding: 8px 23px 10px 16px;
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
}
.img-author {
  height: 23px;
  width: 23px;
  border-radius: 5px;
}
.author {
  margin-left: 10px;
  font-size: 14px;
  color: #5a5a5a;
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
  margin: 0 18px 0 18px;
  font-size: 14px;
  color: #5a5a5a;
}
.date {
  font-size: 14px;
  color: #5a5a5a;
}
.infinite-message {
  font-size: 14px;
  margin: 130px 0 80px 0;
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
</style>