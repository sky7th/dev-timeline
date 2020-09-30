<template>
  <div class="sort-button-wrapper">
    <span @click="onClick" class="sort-button">
      {{ getDescription(type) }}
    </span>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core' 

faLibrary.add() 

export default {
  props: ['typeMap', 'type', 'updateType'],
  methods: {
    getDescription(type) {
      return this.typeMap[type];
    },
    ...mapActions(['resetOffset', 'updatePosts', 'updateIsClickedClickMenu', 'updateClickMenuList', 'updateClickMenuLocation']),
    onClick(event) {
      event.stopPropagation();
      if (this.isClickedClickMenu) {
        this.updateIsClickedClickMenu(false);
        this.updateClickMenuList([]);
        this.updateClickMenuLocation([])
      } 
      else {
        this.updateIsClickedClickMenu(true);
        let x = event.clientX;
        let y = event.clientY;
        this.updateClickMenuLocation([x - 55, y + 13]);
        this.updateClickMenuList(this.getClickMenuList());
      }
    },
    getClickMenuList() {
      return Object.keys(this.typeMap).map(key => {
        return {
          name: this.typeMap[key], 
          func: () => {
            event.stopPropagation();
            this.updateType(key);
            this.resetOffset();
            this.updatePosts();
            }
        }
      });
    }
  },
}
</script>

<style scoped>
.sort-button-wrapper {
  text-align: right;
}
.sort-button-wrapper + .sort-button-wrapper {
  padding-left: 10px;
}
.sort-button {
  display: inline-block;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14.5px;
  padding: 6px 9px;
  color: #555;
  outline: none;
  cursor: pointer;
}
.sort-button:hover {
  background-color: #eee;
}
@media screen and (max-width: 480px) {
  .sort-button {
    font-size: 14.5px;
    padding: 6px 9px;
    color: #555;
    outline: none;
    cursor: pointer;
  }
}
</style>