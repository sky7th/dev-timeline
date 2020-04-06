<template>
  <div class="content">
    <Recruit/>
  </div>
</template>

<script>
import Recruit from '@/components/content/Recruit';

export default {
  components: {
    Recruit
  }
}
</script>

<style scoped>
.content {
  height: 100%;
  width: 100%;
  position: absolute;
  margin-left: 100px;
  margin-top: 50px;
  animation: fadeIn 0.3s ease-in-out;
  z-index: 900;
}
</style>