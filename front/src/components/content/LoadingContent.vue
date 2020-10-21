<template>
  <div class="loading-content-background" :class="{ 'visible': isLoadingContent }">
    <img src="../../assets/images/loading.gif" class="loading-content"/> 
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(['isLoadingContent'])
  },
}
</script>

<style scoped>
.loading-content-background {
  position: fixed;
  opacity: 0;
  background-color: rgba(120, 120, 120, 0.4);
  z-index: -1;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  transition: 0.3s all ease-in;
  animation: fadeIn 0.3s ease-in-out;
}

.loading-content {
  height: 120px;
}

.visible {
  opacity: 1;
  z-index: 9999;
}
</style>