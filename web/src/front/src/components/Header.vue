<template>
  <div class="header">
    <div class="logo-name">dev-time</div>
    <SearchBar class="search-bar"/>
    <div>login</div>
  </div>
</template>

<script>
import SearchBar from '@/components/common/SearchBar';

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
  background-color: whitesmoke;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 50px;
  border: 1px solid black;
  z-index: 1000;
}
.logo-name {
  font-size: 25px;
  font-weight: bold;
  margin-left: 15px;
}
.search-bar {

}
</style>