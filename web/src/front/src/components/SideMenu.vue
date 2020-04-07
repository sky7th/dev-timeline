<template>
  <div class="side-menu">
    <ul>
      <li @click="handleUpdateSelectedMenu('recruit-posts')">
        <div class="alpha">R</div>
        <div class="description">채용</div>
      </li>
      <li @click="handleUpdateSelectedMenu('tech-posts')">
        <div class="alpha">B</div>
        <div class="description">블로그</div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  methods: {
    ...mapActions(['updateSelectedMenu', 'updatePosts']),
    handleUpdateSelectedMenu(val) {
      this.updateSelectedMenu({ selectedMenu: val })
      this.updatePosts()
    }
  }
}
</script>

<style scoped>
.side-menu {
  /* border-right: 4px dashed lightgray; */
  position: fixed;
  left: 0;
  bottom: 0;
  background-color: white;
  top: 57px;
  height: 100%;
  width: 72px;
  z-index: 1000;
}
li {
  text-align: center;
  padding: 16px 0;
  cursor: pointer;
  color: #4E5763;
}
li:hover {
  background-color: #eaeaea;
}
.alpha {
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 3px;
}
.description {
  font-size: 13px;
  font-weight: 600;
}
</style>