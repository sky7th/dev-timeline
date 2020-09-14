<template>
  <div class="paging-bar">
    <div class="inner-paging">
      <a v-for="(pageNumber, i) in getPageNumbers()" 
        :key="i" 
        class="page-numbers" 
        :class="{ current: pageNumber==page }"
        @click="handlerPageNumber(pageNumber)"
      >{{ pageNumber + 1}}
      </a>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Constant from '@/constant/Constant';

export default {
  data() {
    return {
      pageLimit: 3,
      pageNumbers: [],
      page: 0
    }
  },
  computed: {
    ...mapGetters(['postCounts']),
  },
  methods: {
    ...mapActions(['updatePosts', 'updateOffset']),
    getPageNumbers() {
      var pageNumbers = [];
      var lastPage = Math.ceil(this.postCounts / Constant.POST_LIMIT) - 1

      for (var i = -this.pageLimit; i <= this.pageLimit; i++) {
        if (this.page + i < 0) {
          if (this.pageLimit - i > lastPage)
            continue
          pageNumbers.push(this.pageLimit - i)
        } else {
          if (this.page + i > lastPage)
            continue
          pageNumbers.push(this.page + i)
        }
      }
      pageNumbers.sort((a, b) => a - b)

      return pageNumbers;
    },
    handlerPageNumber(page) {
      this.page = page
      scroll(0, 0)
      this.updateOffset({ offset: page * Constant.POST_LIMIT })
      this.updatePosts()
    }
  }
}
</script>

<style scoped>
.paging-bar {
  margin: 5px 0 80px 0;
  font-size: 0;
  text-align: center;
}
.paging-bar .inner-paging .current {
  border-radius: 20px;
  background-color: #2d92f1;
  color: #fff;
  pointer-events: none;
}
.paging-bar .page-numbers, .paging-bar .page-numbers+.page-numbers {
    margin-left: 8px;
}
.paging-bar .page-numbers {
    display: inline-block;
    line-height: 37px;
    vertical-align: top;
    overflow: hidden;
    width: 36px;
    height: 36px;
    font-size: 15px;
  /* font-family: 'campton', 'Noto Sans', 'Apple SD Gothic Neo', '맑은 고딕', 'Malgun Gothic', '돋움', dotum, sans-serif; */
  color: #03166C;
  background-color: #fff;
  transition: border-radius .3s;
  cursor: pointer;
  box-shadow: 0 1px 2px rgba(27,31,35,.1);
}
.paging-bar .page-numbers:hover {
  color: white;
  transition: color .3s;
  background-color: #2d92f1;
  transition: background-color .3s;
}
</style>