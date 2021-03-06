<template>
  <div class="link-posts">
    <FixedTagBar/>
    <div class="filter-button">
      <CountBar/>
      <div class="filter-button-wrapper">
        <FilterButton v-if="this.isClickedMyLike" :typeMap=filterTypeMap :type=filterType :updateType=updateFilterType />
        <FilterButton :typeMap=orderTypeMap :type=sortOrder :updateType=updateSortOrder />    
      </div>
    </div>
    <div class="content-container">
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
            commentCount,
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
                <div class="date">{{ timeForToday(createdDate) }}</div>
              </div>
            </div>
          </a>
          <Like :isLike="like" :postId="id" :likeCount="likeCount" class="like-wrapper"/>
          <div class="post-container-btn comment-icon">
            <div class="like">
              <div class="btn-comment">
                <font-awesome-icon :icon="['far', 'comment']"/>
              </div>
              <span class="like-count">{{ commentCount }}</span>
            </div>
          </div>
          <NewIcon :date="createdDate" :period="2" class="new-icon"/>
        </li>
        <infinite-loading class="infinite-message" @infinite="handlerInfinite" ref="infiniteLoading"  spinner="waveDots">
          <div slot="no-more">
            <ScrollUp/>
          </div>
          <div slot="no-results">
            <ScrollUp/>
          </div>
        </infinite-loading>
      </ul>
    </div>
  </div>
</template>

<script>
import CountBar from '@/components/search/CountBar';
import FixedTagBar from '@/components/search/FixedTagBar';
import ScrollUp from '@/components/common/ScrollUp'
import Like from '@/components/like/Like'
import NewIcon from '@/components/common/NewIcon'
import FilterButton from '@/components/sortButton/FilterButton'
import { mapGetters, mapActions } from "vuex";
import InfiniteLoading from 'vue-infinite-loading';
import Constant from '@/constant/Constant';
import { fas } from '@fortawesome/free-solid-svg-icons' 
import { far } from '@fortawesome/free-regular-svg-icons' 
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core' 
import notification from '../../libs/notification';
import { timeForToday } from '@/utils/time';

faLibrary.add(fas, far) 

export default {
  components: {
    InfiniteLoading,
    CountBar,
    FixedTagBar,
    ScrollUp,
    Like,
    NewIcon,
    FilterButton
  },
  data: () => ({
      filterTypeMap: {
        'ALL': '모든 글',
        'MY_POST': '내가 쓴 글',
        'LIKED': '좋아요 한 글'
      },
      orderTypeMap: {
        'LIKE': '인기 순',
        'DESC': '최신 순',
        'ASC': '오래된 순'
      },
  }),
  computed: {
    ...mapGetters(['posts', 'isClickedMyLike', 'filterType', 'sortOrder'])
  },
  watch: {
    posts() {
      if (this.$refs.infiniteLoading) {
        this.$refs.infiniteLoading.stateChanger.reset(); 
      }
    }
  },
  methods: {
    ...mapActions(['insertPosts', 'onModalState', 'resetOffset', 'updatePosts', 'updatePostState', 'updatePost', 'updateModalContent', 'updateFilterType', 'updateSortOrder']),
    handlerInfinite($state) {
      this.insertPosts({ infiniteState: $state });
    },
    handlerOnModalState(postId) {
      this.axios.get(`${process.env.VUE_APP_API}/api/v1/link-posts/${postId}`)
        .then(response => {
          this.updatePost(response.data)
          this.updateModalContent('LINK')
          this.updatePostState(Constant.READ);
          this.onModalState();
        }).catch(() => {
          notification.warn('글을 불러오지 못했습니다.');
        })
    },
    timeForToday(createdDate) {
        return timeForToday(createdDate);
    }
  }
}
</script>

<style scoped>
.content-container {
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: scroll;
  height: 100%;
  width: 100%;
}
.link-posts {
  height: 100%;
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
.link-posts ul {
  text-align: center;
  padding: 5px 10px 200px 10px;
  max-width: 1200px;
  width: 100%;
}
.link-posts ul li {
  position: relative;
  width: 335px;
  display: inline-flex;
  justify-content: flex-end;
  margin: 10px 20px;
  background-color: white;
  margin-bottom: 15px;
  border-radius: 10px;
  -webkit-animation: fadeIn 0.3s ease-in-out;
  animation: fadeIn 0.3s ease-in-out;
  box-shadow: 0 1px 2px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
}
.link-posts ul li:hover {
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
  font-size: 12px;
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
  font-size: 16px;
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
  font-size: 12px;
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
  width: 88%;
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
  bottom: 9px;
  left: 15px;
  color: #7a7a7a;
  font-size: 14px;
}
.like-count {
  margin-left: 4px;
  font-size: 12px;
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
.btn-comment {
    display: inline-block;
}
.btn-like-full {
  color: #ff5784
}
.comment-icon {
    margin-left: 40px;
}
.like-wrapper {
  bottom: 10px;
  left: 15px;
}
.new-icon {
  right: 24px;
  top: 10px;
  text-align: end;
}
.filter-button {
  text-align: center;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}
.filter-button-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  text-align: center;
  padding: 0px 20px 0px 20px;
  width: 1125px;
}
@media screen and (max-width: 480px) {
    .link-posts ul {
      width: 100%;
    }
    .link-posts ul li {
        width: 100%;
        margin: 0 0 15px 0;
    }
    .like-count {
      margin-left: 4px;
      font-size: 13px;
    }
    .post-container-btn {
      font-size: 17px;
    }
    .like-wrapper {
      bottom: 9px;
    }
    .comment-icon {
      margin-left: 50px;
    }
    .filter-button-wrapper {
      text-align: right;
      padding-right: 11px;
    }
}
</style>