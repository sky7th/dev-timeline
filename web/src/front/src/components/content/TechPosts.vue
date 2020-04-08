<template>
  <div class="tech-posts">
    <CompanyList/>
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
    <div class="paging_comm">
      <div class="inner_paging">
        <a v-for="(pageNumber, i) in getPageNumbers()" 
          :key="i" 
          class="page-numbers" 
          :class="{ current: pageNumber==page }"
          @click="handlerPageNumber(pageNumber)"
        >{{ pageNumber + 1}}
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import CompanyList from '@/components/company/CompanyList';
import { mapGetters, mapActions } from "vuex";
import { POST_LIMIT } from '@/constant/Constant';

export default {
  data() {
    return {
      pageLimit: 2,
      pageNumbers: [],
      page: 0
    }
  },
  components: {
    CompanyList
  },
  created() {
    this.updatePosts()
  },
  computed: {
    ...mapGetters(['posts'])
  },
  methods: {
    ...mapActions(['updatePosts', 'updateOffset']),
    getPageNumbers() {
      var pageNumbers = [];
      for (var i = -this.pageLimit; i <= this.pageLimit; i++) {
        if (this.page + i < 0)
          pageNumbers.push(this.pageLimit - i)
        else
          pageNumbers.push(this.page + i)
      }
      pageNumbers.sort()

      return pageNumbers;
    },
    handlerPageNumber(page) {
      this.page = page
      scroll(0, 0)
      this.updateOffset({ offset: page * POST_LIMIT })
      this.updatePosts()
    }
  }
}
</script>

<style scoped>
.tech-posts ul {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px 13%;
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
  min-width: 288px;
  min-height: 120px;
}
.right .thumbnail-no {
  display: flex;
  width: 100%;
  border-radius: 13px;
  background-color: #f6f6f6;
  justify-content: center;
  min-width: 280px;
  min-height: 121px;
  align-items: center;
}
.right .thumbnail-no div {
  text-align: center;
  font-size: 13px;
}
.paging_comm {
    margin: 17px 0 80px 0;
    font-size: 0;
    text-align: center;
}
.tech-posts .paging_comm .current {
    border-radius: 20px;
    background-color: #03166c;
    color: #fff;
}
.paging_comm .page-numbers, .paging_comm .page-numbers+.page-numbers {
    margin-left: 8px;
}
.paging_comm .page-numbers {
    display: inline-block;
    line-height: 43px;
    vertical-align: top;
    overflow: hidden;
    width: 40px;
    height: 40px;
    font-size: 16px;
    font-family: 'campton', 'Noto Sans', 'Apple SD Gothic Neo', '맑은 고딕', 'Malgun Gothic', '돋움', dotum, sans-serif;
    color: #03166C;
    background-color: #fff;
    transition: border-radius .3s;
}
</style>