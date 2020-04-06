<template>
  <div v-if="(tags.length > 0)" class="search-bar">
    <button class="btn-all-remove" @click="handleRemoveAllTag">x</button>
    <ul>
      <li 
        v-for="({ id, tagName }) in tags" 
        :key="id"
      >
        <div class="tag">
          <span>#{{ tagName }}</span>
          <span class="btn-remove" @click="handleRemoveTag(id)">x</span>
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
.search-bar {
  display: flex;
  padding: 10px 15px;
  border-bottom: 1px dashed black;
}
.search-bar ul {
  display: flex;
}
.tag {
  font-weight: bold;
  margin: 5px 10px;
  padding: 0 10px 0 13px;
  display: block;
	background-color: dodgerblue;
	height: 37px;
	line-height: 32px;
	color: #fff;
	overflow: hidden;
	border-radius: 5px;
	box-shadow: 2px 2px 4px 0 rgba(0,0,0,.4)
}
.btn-remove {
  margin: 0 3px 0 12px;
  font-weight: bold;
  font-size: 22px;
  cursor: pointer;
  padding-bottom: 4px;
  padding-left: 10px;
  border-left: 1px dashed white;
}
.btn-all-remove {
  font-weight: bold;
  margin: 5px 10px;
  padding: 0px 11px 3px 11px;
  display: block;
  background-color: royalblue;
  height: 37px;
  font-size: 22px;
  color: #fff;
  overflow: hidden;
  border-radius: 25px;
}
</style>