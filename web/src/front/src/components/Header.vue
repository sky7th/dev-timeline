<template>
  <div class="header">
    <a href="/" class="logo-name">dev-time</a>
    <SearchBar/>
    <button class="btn-login">로그인</button>
  </div>
</template>

<script>
import SearchBar from '@/components/search/SearchBar';

export default {
  components: {
    SearchBar
  }
}
</script>

<style scoped>
.header {
  position: fixed;
  top: 0;
  width: 100%;
  padding: 0 40px;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 57px;
  /* border: 1px solid black; */
  z-index: 1000;
}
.logo-name {
  font-size: 25px;
  font-weight: bold;
  color: #4E5763;
  cursor: pointer;
}
.btn-login {
  color: #4E5763;
  font-weight: 600;
  font-size: 18px;
  background-color: white;
  border: none;
  cursor: pointer;
  height: 50px;
}
</style>