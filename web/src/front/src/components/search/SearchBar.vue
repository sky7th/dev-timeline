<template>
  <div class="search-bar">
    <input type="text" v-model="searchName" @keypress="handleSearchPressEnter">
    <button @click="handleSearch">
        <font-awesome-icon :icon="['fas', 'search']" class="menu-bars"/>
    </button>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  data() {
    return {
      searchName: ''
    }
  },
  watch: {
    searchName(val) {
      this.searchName = val
    }
  },
  methods: {
    ...mapActions(['updatePosts', 'resetOffset', 'insertTag']),
    handleSearch() {
      this.insertTag({ tagName: this.searchName })
      this.resetOffset()
      this.updatePosts()
      this.searchName = ''
    },
    handleSearchPressEnter({ keyCode }) {
      if (keyCode === 13) {
        this.handleSearch();
      }
    }
  }
}
</script>

<style scoped>
.search-bar {
    width: 50%;
    display: flex;
}
.search-bar input {
  height: 32px;
  width: 100%;
  border: 1.5px solid #bababa;
  border-radius: 4px 0 0 4px;
  box-shadow: 0px 0px 1px 0px rgba(0, 0, 0, 0.2) inset;
  padding: 0 8px;
}
.search-bar button {
  height: 32px;
  width: 80px;
  color: #4E5763;
  font-weight: 600;
  margin-left: -2px;
  background-color: #f2f2f2;
  font-size: 15px;
  border: 1.5px solid #bababa;
  border-radius: 0px 4px 4px 0px;
  box-shadow: 0px 0px 1px 0px rgba(0, 0, 0, 0.2) inset;
  cursor: pointer;
}
.search-bar button:hover {
  background-color: #eaeaea;
}
@media screen and (max-width: 480px) {
    .search-bar {
        width: 100%;
        display: flex;
    }
    .search-bar input {
        width: 100%;
    }
    .search-bar button {
        width: 60px;
    }
}
</style>