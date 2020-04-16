<template>
  <div class="count-bar">
    <span class="count">{{ postCounts }}</span>
    <span>{{ handlerCountDescription() }}</span>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(['selectedMenu', 'postCounts']),
  },
  methods: {
    handlerCountDescription() {
      switch (this.selectedMenu) {
        case 'recruit-posts':
          return ' 개의 채용정보 글이 있어요'
        case 'tech-posts':
          return ' 개의 블로그 글이 있어요'
        case 'link-posts':
          return ' 개의 링크 공유 글이 있어요'
        default:
          return ''
      }
    }
  }
}
</script>

<style scoped>
.count-bar {
  text-align: center;
  font-size: 14px;
  -webkit-animation: fadeIn 1s ease-in-out;
  animation: twinkle 1s ease-in-out;
  animation-iteration-count: 9999;
}
.count-bar .count {
  
}
</style>