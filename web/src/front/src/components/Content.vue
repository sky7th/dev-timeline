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
  height: 100%;
  width: 100%;
  position: absolute;
  margin-left: 100px;
  margin-top: 60px;
  animation: fadeIn 0.3s ease-in-out;
  z-index: 900;
}
</style>