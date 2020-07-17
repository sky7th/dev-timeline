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
  padding: 7px 5px 4px 5px;
  border-bottom: 1px dashed black;
  margin-top: -57px;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
.tag-bar ul {
  width: 100%;
}
.tag-bar ul li {
  display: inline-block;
}
.tag-bar ul::-webkit-scrollbar { width: 3.2px; } /* 스크롤 바 */
.tag-bar ul::-webkit-scrollbar-track { background-color:#f7f7f7; } /* 스크롤 바 밑의 배경 */
.tag-bar ul::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
.tag-bar ul::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
.tag-bar ul::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
.tag-bar ul::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
.tag {
  margin: 5px 10px 5px 0px;
  padding-left: 10px;
  display: block;
  background-color: #2d92f1;
  height: 27px;
  line-height: 24px;
  color: #fff;
  font-size: 12px;
  overflow: hidden;
  border-radius: 5px;
  white-space: pre;
  box-shadow: 1px 1px 3px 0 rgba(0,0,0,.2);
  -webkit-animation: fadeIn 0.15s ease-in-out;
  animation: fadeIn 0.15s ease-in-out;
}
.tag:hover {
  background-color: #0d72d1;
}
.btn-remove {
  margin-left: 8px;
  font-size: 14px;
  cursor: pointer;
  padding: 3px 9px 6px 7px;
  border-left: 1px dashed white;
}
.btn-all-remove {
  font-weight: bold;
  margin: 6px 10px 3px 6px;
  padding: 0px 10px 4px 9px;
  display: block;
  background-color: #bebebe;
  height: 26px;
  width: 27px;
  font-size: 16px;
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