<template>
  <div class="chat-bottom"  @click="handlerChangeChatOpenState()">
    {{ convertNameSelectedChatRoom() }}
  </div>
</template>

<script>
export default {
  props: {
    selectedChatRoom: { type: Object, default: () => ({
      roomId: '',
      name: '',
    })}
  },
  methods: {
    handlerChangeChatOpenState() {
      this.$emit('changeChatOpenState');
    },
    convertNameSelectedChatRoom() {
      if (this.selectedChatRoom.name === 'recruit-posts')
        return '채용 채팅방';
      else if (this.selectedChatRoom.name === 'tech-posts')
        return '테크 블로그 채팅방';
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
  box-shadow: 0 1px 2px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
  padding-top: 6px;
  font-size: 14px;
  font-weight: bold;
}
.chat-bottom:hover {
  background-color: #eeeeee;
}
</style>