<template>
  <div class="tech-posts">
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
          author,
          date,
          thumbnailUrl,
          contentUrl }) in posts"
        :key="id"
      >
        <a :href="contentUrl" style="display: flex;" target="_blank">
          <div class="left">
            <img class="logo" :src="companyLogoUrl" alt="">
            <div class="company">{{ companyTypeName }}</div>
          </div>
          <div class="middle">
            <div class="title">{{ title }}</div>
            <div class="middle-bottom">
              <div class="author">{{ author }}</div>
              <div class="between" v-if="author">|</div>
              <div class="date">{{ date }}</div>
            </div>
          </div>
          <div class="right">
            <img class="thumbnail" v-if="thumbnailUrl" :src="thumbnailUrl" alt="">
            <div class="thumbnail-no" v-else>
              <div>미리보기 이미지가 없어용.. ㅠ.ㅠ</div>
            </div>
          </div>
          
          <!-- <div>{{ companyUrlTypeName }}</div> -->
        </a>
      </li>
    </ul>
    <PagingBar v-if="postCounts > 0"/>
  </div>
</template>

<script>
import CompanyList from '@/components/company/CompanyList';
import CountBar from '@/components/search/CountBar';
import PagingBar from '@/components/search/PagingBar';
import { mapGetters, mapActions } from "vuex";

export default {
  components: {
    CompanyList,
    CountBar,
    PagingBar
  },
  created() {
    this.updatePosts()
  },
  computed: {
    ...mapGetters(['posts', 'postCounts'])
  },
  methods: {
    ...mapActions(['updatePosts'])
  }
}
</script>

<style scoped>
.tech-posts ul {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px 13%;
}
.tech-posts ul li {
  background-color: white;
  padding: 10px 20px;
  margin-bottom: 15px;
  border-radius: 10px;
  width: 100%;
  min-height: 140px;
  -webkit-animation: fadeIn 0.3s ease-in-out;
  animation: fadeIn 0.3s ease-in-out;
  box-shadow: 0 1px 4px rgba(27,31,35,.1);
}
.tech-posts ul li:hover {
  box-shadow: 0 1px 6px rgba(27,31,35,.3);
}
.logo {
  height: 50px;
  width: 50px;
  margin-bottom: 5px;
}
.company {
  text-align: center;
}
.left {
  justify-content: center;
  flex-direction: column;
  display: flex;
  width: 100px;
  align-items: center;
  margin-right: 20px;
}
.middle {
  flex: 3;
  justify-content: center;
  flex-direction: column;
  display: flex;
  padding-right: 40px;
}
.title {
  padding-bottom: 20px;
  font-size: 18px;
  font-weight: bold;
  text-align: end;
}
.middle-bottom {
  display: flex;
  justify-content: flex-end;
}
.author {
  margin-right: 15px;
  font-size: 15px;
}
.between {
  margin-right: 10px;
}
.date {
  font-size: 15px;
}
.right {
  display: flex;
  flex: 2;
  
}
.right img {
  width: 100%;
  max-height: 150px;
  border-radius: 13px;
  min-height: 120px;
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
  font-size: 13px;
}
</style>