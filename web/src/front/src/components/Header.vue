<template>
  <div class="header">
    <div>dev-time</div>
    <input type="text">
  </div>
</template>

<script>
export default {
  
}
</script>

<style scoped>
.header {
  position: fixed;
  top: 0;
  width: 100%;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 50px;
  border: 1px solid black;
  z-index: 1000;
}
</style>