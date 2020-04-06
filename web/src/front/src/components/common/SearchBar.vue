<template>
  <div class="search-bar">
    <input type="text" v-model="searchName">
    <button @click="search">검색</button>
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
    ...mapActions(['updatePostsByTitle', 'resetOffset']),
    search() {
      this.resetOffset()
      this.updatePostsByTitle({ title: this.searchName })
    }
  }
}
</script>

<style scoped>

</style>