<template>
  <div class="search-bar">
    <button @click="handleRemoveAllTag">All Tag Remove</button>
    <ul>
      <li 
        v-for="({ id, tagName }) in tags" 
        :key="id"
      >
        <div class="tag">
          <span>#{{ tagName }}</span>
          <span class="btn-remove" @click="handleRemoveTag(id)">X</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  data() {
    return {
      searchName: ''
    }
  },
  computed: {
    ...mapGetters(['tags']),
  },
  methods: {
    ...mapActions(['removeTag', 'removeAllTag', 'updatePosts', 'resetOffset']),
    handleRemoveTag(id) {
      this.removeTag({ id: id })
      this.resetOffset()
      this.updatePosts()
    },
    handleRemoveAllTag() {
      this.removeAllTag()
      this.resetOffset()
      this.updatePosts()
    }
  }
}
</script>

<style scoped>
.search-bar ul {
  display: flex;
}
.tag {
  margin: 5px 10px;
}
.btn-remove {
  margin: 0 5px;
}
</style>