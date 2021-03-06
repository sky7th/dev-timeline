<template>
  <div class="recruit-posts">
    <CompanyList/>
    <div class="filter-button">
      <CountBar/>
      <div class="filter-button-wrapper">
        <FilterButton :typeMap=orderTypeMap :type=sortOrder :updateType=updateSortOrder />  
      </div> 
    </div>
    <div class="content-container">
      <ul>
        <li
          v-for="({
            id, 
            company,
            title,
            closingDate,
            sortDate,
            contentUrl,
            like,
            likeCount }) in posts"
          :key="id"
          :class="{
            naver: company.name==='네이버',
            kakao: company.name==='카카오'
          }"
        >
          <Like :isLike="like" :postId="id" :likeCount="likeCount" class="like-wrapper"/>
          <a :href="contentUrl" target="_blank">
            <img class="logo" :src="company.logoUrl" alt="">
              <div>
                <div class="date">{{ closingDate }}</div>
              </div>
            <div class="title">{{ title }}</div>
          </a>
          <NewIcon :date="sortDate" :period="5"/>
        </li>
        <infinite-loading class="infinite-message" @infinite="handlerInfinite" ref="infiniteLoading" spinner="waveDots">
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
import CompanyList from '@/components/company/CompanyList';
import CountBar from '@/components/search/CountBar';
import ScrollUp from '@/components/common/ScrollUp'
import NewIcon from '@/components/common/NewIcon'
import Like from '@/components/like/Like'
import { mapGetters, mapActions } from "vuex";
import InfiniteLoading from 'vue-infinite-loading';
import FilterButton from '@/components/sortButton/FilterButton'

export default {
  components: {
    CompanyList,
    InfiniteLoading,
    CountBar,
    ScrollUp,
    Like,
    NewIcon,
    FilterButton
  },
  data: () => ({
      orderTypeMap: {
        'LIKE': '인기 순',
        'DESC': '최신 순',
        'ASC': '오래된 순'
      },
  }),
  watch: {
    posts() {
      if (this.$refs.infiniteLoading) {
        this.$refs.infiniteLoading.stateChanger.reset(); 
      }
    }
  },
  computed: {
    ...mapGetters(['posts', 'sortOrder'])
  },
  methods: {
    ...mapActions(['insertPosts', 'updateSortOrder']),
    handlerInfinite($state) {
      this.insertPosts({ infiniteState: $state })
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
.recruit-posts {
  height: 100%;
}
.recruit-posts ul {
  padding: 5px 5px 200px 5px;
  text-align: center;
  max-width: 1500px;
  width: 100%;
}
.recruit-posts ul li {
  padding: 20px 20px;
  width: 260px;
  padding: 0 25px;
  display: inline-flex;
  margin: 10px 15px;
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
  margin-top: -11px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.logo {
  height: 60px;
  width: 60px;
  border-radius: 8px;
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
  margin-bottom: 10px;
  margin-top: -9px;
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
.filter-button {
  text-align: center;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 10px;
  justify-content: center;
}
.filter-button-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  text-align: center;
  padding: 0px 20px 0px 20px;
  width: 1460px;
}
@media screen and (max-width: 680px) { 
    .recruit-posts ul {
      /* display: grid;
      grid-template-columns: 1fr 1fr; */
      align-items: baseline;
      justify-items: center;
    }
    .recruit-posts ul li {
      width: 47%;
      padding: 0 25px;
      display: inline-flex;
      margin: 4px 4px;
      padding: 8px 15px 8px 15px; 
    }
    .recruit-posts ul li a {
      width: 100%;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      margin-top: -3px;
    }
    .logo {
      height: 50px;
      margin-bottom: 8px;
    }
    .filter-button-wrapper {
      text-align: right;
      padding-right: 18px;
    }
    .date {
      font-size: 13px;
    }
    .title {
      font-size: 13px;
      line-height: 160%;
    }
    .like-wrapper {
      right: 14px;
    }
}
@media screen and (max-width: 425px) { 
    .date {
      font-size: 12px;
    }
    .title {
      font-size: 12px;
      line-height: 160%;
    }
}
@media screen and (max-width: 340px) { 
    .recruit-posts ul {
      display: block;
    }
}
</style>