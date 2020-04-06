<template>
  <div class="side-menu">
    <ul>
      <li @click="handleUpdateSelectedMenu('recruit-posts')">채용</li>
      <li @click="handleUpdateSelectedMenu('tech-posts')">기술 블로그</li>
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
  border-right: 1px solid black;
  position: fixed;
  left: 0;
  bottom: 0;
  background-color: white;
  top: 60px;
  height: 100%;
  width: 100px;
  z-index: 1000;
}
li {
  text-align: center;
  margin: 20px 0;
}
</style>