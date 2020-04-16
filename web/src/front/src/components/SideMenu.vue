<template>
  <div class="side-menu">
    <ul>
      <li :class="{ 'selected-menu': selectedMenu=='recruit-posts' }" @click="handleUpdateSelectedMenu('recruit-posts')">
        <div class="alpha">R</div>
        <div class="description">채용</div>
      </li>
      <li :class="{ 'selected-menu': selectedMenu=='tech-posts' }" @click="handleUpdateSelectedMenu('tech-posts')">
        <div class="alpha">B</div>
        <div class="description">블로그</div>
      </li>
      <li :class="{ 'selected-menu': selectedMenu=='link-posts' }" @click="handleUpdateSelectedMenu('link-posts')">
        <div class="alpha">L</div>
        <div class="description">링크</div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  computed: {
    ...mapGetters(['selectedMenu'])
  },
  methods: {
    ...mapActions(['resetAll', 'updateSelectedMenu']),
    handleUpdateSelectedMenu(val) {
      this.resetAll()
      this.updateSelectedMenu({ selectedMenu: val })
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
.selected-menu {
  pointer-events: none;
  background-color: #eaeaea;
}
</style>