<template>
  <div class="content">
    <TagBar/>
    <RecruitPosts v-if="selectedMenu==='recruit-posts'"/>
    <TechPosts v-else-if="selectedMenu==='tech-posts'"/>
  </div>
</template>

<script>
import RecruitPosts from '@/components/content/RecruitPosts';
import TechPosts from '@/components/content/TechPosts';
import TagBar from '@/components/search/TagBar';

import { mapGetters } from "vuex";

export default {
  components: {
    RecruitPosts,
    TechPosts,
    TagBar
  },
  computed: {
    ...mapGetters(['selectedMenu'])
  }
}
</script>

<style scoped>
.content {
  height: auto;
  width: 100%;
  height: auto;
  min-height: 100%;
  position: absolute;
  box-sizing: border-box;
  padding-left: 72px;
  padding-top: 57px;
  -webkit-animation: fadeIn 0.3s ease-in-out;
  animation: fadeIn 0.3s ease-in-out;
  z-index: 900;
  background-color: #f7f7f7;
}
</style>