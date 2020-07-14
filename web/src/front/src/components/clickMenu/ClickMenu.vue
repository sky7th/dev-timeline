<template>
  <div v-if="isClickedClickMenu" id="click-menu" :style="{'left': `${clickMenuLocation[0]}px`, 'top': `${clickMenuLocation[1]}px`}">
    <ul class="click-menu-list">
      <li class="click-menu-item"
        v-for="({
          name,
          func}) in clickMenuList"
        :key="name"
        @click="onClick(func)"
      >
        <div>{{name}}</div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core' 

faLibrary.add() 

export default {
  computed: {
    ...mapGetters(['clickMenuList', 'isClickedClickMenu', 'clickMenuLocation'])
  },
  mounted() {
    window.onkeyup = () => {
      if (event.keyCode == 27) {
        this.closeClickMenu();
      }
    }
    window.onclick = () => {
      this.closeClickMenu();
    }
  },
  methods: {
    ...mapActions(['updateIsClickedClickMenu', 'updateClickMenuList', 'updateClickMenuLocation']),
    closeClickMenu() {
      this.updateIsClickedClickMenu(false);
      this.updateClickMenuList([]);
      this.updateClickMenuLocation([])
    },
    onClick(func) {
      func();
      this.closeClickMenu();
    }
  },
}
</script>

<style scoped>
#click-menu {
  position: fixed;
  z-index: 1000;
  padding-top: 4px;
  padding-bottom: 4px;
  margin-top: 2px;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #e1e4e8;
  border-radius: 6px;
  box-shadow: 0 8px 24px rgba(149,157,165,.2);
}
.click-menu-list {
  font-size: 14px;
  line-height: 1.5;
}
.click-menu-item:hover {
  background-color: #2d92f1;
}
.click-menu-item div {
  display: block;
  padding: 4px 20px 4px 16px;
  overflow: hidden;
  color: #24292e;
  text-overflow: ellipsis;
  white-space: nowrap;
  cursor: pointer;
}
.click-menu-item div:hover {
  color: white;
}
</style>