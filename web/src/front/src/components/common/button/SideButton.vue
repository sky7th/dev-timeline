<template>
  <div class="side-button">
    <button class="list-group-item list-group-item-action" @click="handleEvent">
      <div>글쓰기</div>
    </button>
  </div>
</template>

<script>
export default {
  methods: {
    handleEvent() {
      this.$emit("event");
    },
  }
}
</script>

<style scoped>
.side-button {
  position: fixed;
  right: 0;
  z-index: 2000;
  animation: fadeIn 0.3s ease-in-out;
}
.side-button button {
  border: 0px solid black;
  padding: 10px;
  cursor: pointer;
  background-color: white;
  color: #2d92f1;
  font-weight: 500;
  font-size: 13px;
  box-shadow: 0 1px 3px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
}
.side-button button:hover {
  background-color: #2d92f1;
  color: #fff;
  transition: background-color .3s;
}
</style>