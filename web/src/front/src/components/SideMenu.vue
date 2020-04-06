<template>
  <div class="side-menu">
    <ul>
      <li>채용</li>
      <li>기술 블로그</li>
    </ul>
  </div>
</template>

<script>
export default {
  
}
</script>

<style scoped>
.side-menu {
  border-right: 1px solid black;
  position: fixed;
  left: 0;
  bottom: 0;
  background-color: white;
  top: 50px;
  height: 100%;
  width: 100px;
  z-index: 1000;
}
li {
  text-align: center;
  margin: 20px 0;
}
</style>