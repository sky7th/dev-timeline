<template>
  <div class="sort-button-wrapper">
    <span @click="onClick" class="sort-button">
      {{ sortOrder == 'like' ? '인기순' : (sortOrder == 'desc' ? '최신순' : '오래된순') }}
    </span>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core' 

faLibrary.add() 

export default {
  computed: {
    ...mapGetters(['sortOrder'])
  },
  methods: {
    ...mapActions(['updateSortOrder', 'resetOffset', 'updatePosts', 'updateIsClickedClickMenu', 'updateClickMenuList', 'updateClickMenuLocation']),
    onClick(event) {
      event.stopPropagation();
      if (this.isClickedClickMenu) {
        this.updateIsClickedClickMenu(false);
        this.updateClickMenuList([]);
        this.updateClickMenuLocation([])
      } 
      else {
        this.updateIsClickedClickMenu(true);
        let x = event.clientX + (document.documentElement.scrollLeft?document.documentElement.scrollLeft:document.body.scrollLeft);
        let y = event.clientY + (document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop);
        this.updateClickMenuLocation([x - 55, y + 13]);
        this.updateClickMenuList([
        { name: '인기순', func: this.onClickSortLike },
        { name: '최신순', func: this.onClickSortDesc },
        { name: '오래된순', func: this.onClickSortAsc },
      ]);
      }
    },
    onClickSortLike() {
      event.stopPropagation();
      this.updateSortOrder('like')
      this.resetOffset();
      this.updatePosts();
    },
    onClickSortDesc() {
      event.stopPropagation();
      this.updateSortOrder('desc')
      this.resetOffset();
      this.updatePosts();
    },
    onClickSortAsc() {
      event.stopPropagation();
      this.updateSortOrder('asc')
      this.resetOffset();
      this.updatePosts();
    }
  },
}
</script>

<style scoped>
.sort-button-wrapper {
  text-align: right;
  padding-right: 30px;
  margin-top: 8px;
}
.sort-button {
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
  padding: 5px 8px;
  color: #555;
  outline: none;
  cursor: pointer;
}
.sort-button:hover {
  background-color: #eee;
}
</style>