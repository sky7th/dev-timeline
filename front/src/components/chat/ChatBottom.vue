<template>
  <div class="chat-bottom"  @click="handlerChangeChatOpenState()">
    <span>{{ room.name }}</span>
    <span class="user-count">{{ userCount }}</span>
  </div>
</template>

<script>
export default {
  props: {
    room: { type: Object, default: () => ({
      id: '',
      name: '',
      userCount: 0
    })},
    userCount: { type: Number, default: 0 },
  },
  methods: {
    handlerChangeChatOpenState() {
      this.$emit('changeChatOpenState');
    }
  }
}
</script>

<style scoped>
.chat-bottom {
  position: relative;
  cursor: pointer;
  height: 30px;
  text-align: center;
  background-color: white;
  border-radius: 3px;
  box-shadow: 0 0px 2px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
  padding: 6px;
  font-size: 14px;
  font-weight: bold;
  z-index: 1;
}
.chat-bottom:hover {
  background-color: #eeeeee;
}
.user-count {
  font-weight: bold;
  border-radius: 5px;
  background-color: #eaeaea;
  padding: 2px 4px;
  font-size: 13px;
  margin-left: 9px;
}
</style>