<template>
  <div class="content">
    <!-- <Recruit/> -->
    <TagBar/>
    <RecruitPosts/>
    
  </div>
</template>

<script>
import RecruitPosts from '@/components/content/RecruitPosts';
import TagBar from '@/components/search/TagBar';

export default {
  components: {
    RecruitPosts,
    TagBar
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