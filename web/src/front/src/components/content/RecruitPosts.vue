<template>
  <div class="recruit-posts">
    <CompanyList/>
    <CountBar/>
    <ul>
      <li
        v-for="({
          id, 
          companyLogoUrl,
          companyTypeName, 
          companyUrlTypeName, 
          title,
          closingDate,
          contentUrl,
          like,
          likeCount }) in posts"
        :key="id"
        :class="{
          naver: companyTypeName==='네이버',
          kakao: companyTypeName==='카카오'
        }"
      >
        <Like :isLike="like" :postId="id" :likeCount="likeCount" class="like-wrapper"/>
        <a :href="contentUrl" target="_blank">
          <div style="display: flex;">
            <img class="logo" :src="companyLogoUrl" alt="">
            <div style="width: auto; flex: 1;">
              <div class="company">{{ companyTypeName }}</div>
              <div class="date">{{ closingDate }}</div>
            </div>
          </div>
          <!-- <div>{{ companyUrlTypeName }}</div> -->
          <div class="title">{{ title }}</div>
        </a>
      </li>
    </ul>
    <infinite-loading class="infinite-message" @infinite="handlerInfinite" ref="infiniteLoading" spinner="waveDots">
      <div slot="no-more">
        <ScrollUp/>
      </div>
      <div slot="no-results">
        <ScrollUp/>
      </div>
    </infinite-loading>
    </div>
</template>

<script>
import CompanyList from '@/components/company/CompanyList';
import CountBar from '@/components/search/CountBar';
import ScrollUp from '@/components/common/ScrollUp'
import Like from '@/components/like/Like'
import { mapGetters, mapActions } from "vuex";
import InfiniteLoading from 'vue-infinite-loading';

export default {
  components: {
    CompanyList,
    InfiniteLoading,
    CountBar,
    ScrollUp,
    Like
  },
  watch: {
    posts() {
      if (this.$refs.infiniteLoading) {
        this.$refs.infiniteLoading.stateChanger.reset(); 
      }
    }
  },
  computed: {
    ...mapGetters(['posts'])
  },
  methods: {
    ...mapActions(['insertPosts']),
    handlerInfinite($state) {
      this.insertPosts({ infiniteState: $state })
    }
  }
}
</script>

<style scoped>
.recruit-posts ul {
  padding: 3px 20px;
  text-align: center;
}
.recruit-posts ul li {
  padding: 20px 20px;
  width: 260px;
  padding: 0 25px;
  display: inline-flex;
  margin: 15px;
  padding: 18px 20px 11px 20px;
  border: 1px solid #dedede;
  box-shadow: 0px 0px 1px 0 rgba(0, 0, 0, 0.2);
  box-sizing: border-box;
  cursor: pointer;
  position: relative;
  line-height: 1.5em;
  -webkit-animation: fadeIn 0.3s ease-in-out;
  animation: fadeIn 0.3s ease-in-out;
}
.recruit-posts ul li:after {
  bottom: 4px;
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.3);
  right: 3px;
  transform: skew(1.5deg, 1.5deg);
  position: absolute;
  z-index: -1;
  transition: all 0.4s ease;
  content: "";
  height: 100%;
  width: 100%;
  -webkit-animation: fadeIn 2.5s ease-in-out;
  animation: fadeIn 2.5s ease-in-out;
}
.recruit-posts ul li:hover:after {
  box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.0);
}
.recruit-posts ul li a{
  width: 100%;
}
.logo {
  height: 50px;
  width: 50px;
  border-radius: 8px;
  margin: 0 0 15px 0;
}
.company {
  font-weight: bold;
  margin-bottom: 3px;
  font-size: 15px;
  color: #3f4543;
}
.title {
  font-weight: 500;
  margin-bottom: 5px;
  font-size: 14px;
  color: #3f4543;
}
.date {
  font-weight: bold;
  font-size: 14px;
  color: #3f4543;
}
.naver {
  background-color: rgb(239, 255, 239);
}
.kakao {
  background-color: rgb(255, 255, 215);
}
.infinite-message {
  font-size: 13px;
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
.like-wrapper {
  top: 15px;
  right: 19px;
}
@media screen and (max-width: 380px) { 
    .recruit-posts ul {
        padding: 3px 0px;
    }
    .recruit-posts ul li {
        width: 90%;
        margin: 7px 5px;
    }
}
</style>