<template>
  <div class="loading-content-background">
    <img src="../../assets/images/loading.gif" class="loading-content"/>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>
.loading-content-background {
  background-color: rgba(120, 120, 120, 0.2);
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  transition: 0.125s all ease-in;
  animation: fadeIn 0.3s ease-in-out;
}

.loading-content {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100px;
  transform: translate(-50%, -40%);
  transition: 0.125s all ease-in;
}
</style>