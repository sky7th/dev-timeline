<template>
  <div :class="{ 'tag-bar-on': tags.length > 0 }" class="tag-bar">
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
.tag-bar {
  display: flex;
  padding: 7px 5px 7px 5px;
  border-bottom: 1px dashed black;
  margin-top: -57px;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
.tag-bar ul {
  display: flex;
}
.tag {
  margin: 5px 10px 5px 0px;
  padding-left: 10px;
  display: block;
  background-color: #2d92f1;
  height: 32px;
  line-height: 28px;
  color: #fff;
  font-size: 14px;
  overflow: hidden;
  border-radius: 5px;
  box-shadow: 1px 1px 3px 0 rgba(0,0,0,.2);
  -webkit-animation: fadeIn 0.15s ease-in-out;
  animation: fadeIn 0.15s ease-in-out;
}
.tag:hover {
  background-color: #0d72d1;
}
.btn-remove {
  margin-left: 8px;
  font-size: 18px;
  cursor: pointer;
  padding: 0 9px 5px 7px;
  border-left: 1px dashed white;
}
.btn-all-remove {
  font-weight: bold;
  margin: 7px 15px 3px 10px;
  padding: 0px 10px 3px 10px;
  display: block;
  background-color: #bebebe;
  height: 30px;
  width: 31px;
  font-size: 20px;
  color: #fff;
  border: none;
  overflow: hidden;
  border-radius: 25px;
  cursor: pointer;
}
.btn-all-remove:hover {
  background-color: #9e9e9e;
}
.tag-bar-on {
  margin-top: 0px;
}
</style>