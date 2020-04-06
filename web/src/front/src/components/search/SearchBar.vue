<template>
  <div class="search-bar">
    <input type="text" v-model="searchName" @keypress="handleSearchPressEnter">
    <button @click="handleSearch">검색</button>
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
    ...mapActions(['updatePostsByTags', 'resetOffset', 'insertTag']),
    handleSearch() {
      this.insertTag({ tagName: this.searchName })
      this.resetOffset()
      this.updatePostsByTags()
      this.searchName = ''
    },
    handleSearchPressEnter({ keyCode }) {
      if (keyCode === 13) {
        this.insertTag({ tagName: this.searchName })
        this.resetOffset()
        this.updatePostsByTags()
        this.searchName = ''
      }
    }
  }
}
</script>

<style scoped>

</style>