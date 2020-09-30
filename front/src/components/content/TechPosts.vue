<template>
  <div class="tech-posts">
    <CompanyList/>
    <CountBar/>
    <div class="filter-button">
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
            author,
            date,
            sortDate,
            thumbnailUrl,
            contentUrl,
            like,
            likeCount }) in posts"
          :key="id"
        >
          <Like :isLike="like" :postId="id" :likeCount="likeCount" class="like-wrapper"/>
          <a :href="contentUrl" target="_blank">
            <div class="logo">
              <img class="logo-icon" :src="company.logoUrl" alt="">
            </div>
            <div class="middle">
              <div class="title">{{ title }}</div>
              <div class="middle-bottom">
                <div class="author">{{ author }}</div>
                <div class="between" v-if="author">|</div>
                <div class="date">{{ timeForToday(date) }}</div>
              </div>
            </div>
            <!-- <div v-if="thumbnailUrl" class="right">
              <img class="thumbnail" v-if="thumbnailUrl" :src="thumbnailUrl" alt="">
            </div> -->
            
            <!-- <div>{{ companyUrlTypeName }}</div> -->
          </a>
          <NewIcon :date="sortDate" :period="4" class="new-icon"/>
        </li>
      </ul>
    </div>
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
import Like from '@/components/like/Like';
import NewIcon from '@/components/common/NewIcon'
import { mapGetters, mapActions } from "vuex";
import { timeForToday } from '@/utils/time';
import InfiniteLoading from 'vue-infinite-loading';
import FilterButton from '@/components/sortButton/FilterButton'

export default {
  components: {
    CompanyList,
    CountBar,
    Like,
    NewIcon,
    InfiniteLoading,
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
  created() {
    this.updatePosts()
  },
  computed: {
    ...mapGetters(['posts', 'postCounts', 'sortOrder'])
  },
  methods: {
    ...mapActions(['updatePosts', 'insertPosts', 'updateSortOrder']),
    timeForToday(createdDate) {
        return timeForToday(createdDate);
    },
    handlerInfinite($state) {
      this.insertPosts({ infiniteState: $state });
    },
  }
}
</script>

<style scoped>
.content-container {
  text-align: center;
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.tech-posts ul {
  text-align: center;
  padding: 10px 10px;
  width: 1200px;
}
.tech-posts ul li {
  display: inline-flex;
  position: relative;
  background-color: white;
  padding: 7px 17px 7px;
  margin: 9px 20px;
  border-radius: 10px;
  width: 350px;
  max-width: 550px;
  -webkit-animation: fadeIn 0.3s ease-in-out;
  animation: fadeIn 0.3s ease-in-out;
  box-shadow: 0 1px 4px rgba(27,31,35,.1);
}
.tech-posts ul li:hover {
  box-shadow: 0 1px 6px rgba(27,31,35,.3);
}
.tech-posts ul li a {
  padding-left: 50px;
  display: flex;
  width: 100%;
}
.logo {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: -9px;
  top: -6px;
  height: 45px;
  width: 45px;
  padding: 5px;
  margin-bottom: 5px;
  border-radius: 15px;
  background-color: white;
  box-shadow: 0 1px 4px rgba(27,31,35,.1);
}
.logo-icon {
  width: 100%;
}
.company {
  text-align: center;
  font-size: 12px;
  margin-top: 1px;
}
.left {
  justify-content: center;
  flex-direction: column;
  display: flex;
  width: 60px;
  align-items: center;
  margin-right: 10px;
}
.middle {
  flex: 3;
  justify-content: center;
  flex-direction: column;
  display: flex;
  padding-right: 8px;
}
.title {
  padding-top: 13px;
  padding-bottom: 15px;
  font-size: 16px;
  font-weight: bold;
  text-align: end;
}
.middle-bottom {
  display: flex;
  justify-content: flex-end;
  padding-bottom: 8px;
}
.author {
  margin-right: 15px;
  font-size: 14px;
}
.between {
  margin-right: 10px;
}
.date {
  font-size: 14px;
}
.right {
  display: flex;
  flex: 2;
  
}
.right img {
  width: 100%;
  max-height: 150px;
  border-radius: 13px;
  min-height: 100px;
}
.right .thumbnail-no {
  display: flex;
  width: 100%;
  border-radius: 13px;
  background-color: #f6f6f6;
  justify-content: center;
  min-height: 121px;
  align-items: center;
}
.right .thumbnail-no div {
  text-align: center;
  font-size: 12px;
  margin: 0 17px;
}
.like-wrapper {
  bottom: 9px;
  left: 12px;
}
.new-icon {
  left: 43px;
  top: 5px;
  text-align: end;
}
.filter-button {
  text-align: center;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.filter-button-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  text-align: center;
  padding: 10px 20px 0 20px;
  width: 1170px;
}
@media screen and (max-width: 480px) {
    .tech-posts ul {
        padding: 15px 10px;
    }
    .tech-posts ul li {
        width: 95%;
        margin: 0 0 10px 0;
        padding: 7px 10px 7px;
    }
    .left {
        width: auto;
        margin-left: 10px;
    }
    .middle {
        padding-right: 15px;
    }
    .right {
        display: none;
    }
    .logo {
      height: 35px;
      width: 35px;
    }
    .title {
      font-size: 14px;
    }
    .author, .date {
      font-size: 13px;
    }
    .filter-button-wrapper {
      text-align: right;
      padding-right: 18px;
    }
}
</style>