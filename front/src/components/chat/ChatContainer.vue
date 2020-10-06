<template>
  <div class="chat-container">
    <ChatBottom
      :room="room"
      :userCount="userCount"
      @changeChatOpenState="changeChatOpenState"/>
    <ChatRoom 
      :room="room"
      :class="{ 'chat-hidden': !isChatOpen }"
      @updateUserCount="updateUserCount"/>
  </div>
</template>

<script>
// import { mapGetters, mapActions } from "vuex";
// import notification from '@/libs/notification';
import ChatBottom from '@/components/chat/ChatBottom';
import ChatRoom from '@/components/chat/ChatRoom';

export default {
  props: {
    room: { type: Object, default: () => ({
      id: '',
      name: '',
      userCount: 0
    })}
  },
  components: {
    ChatBottom,
    ChatRoom
  },
  data() {
    return {
      isChatOpen: true,
      userCount: 0
    }
  },
  methods: {
    changeChatOpenState() {
      if (this.isChatOpen == false)
        this.isChatOpen = true
      else
        this.isChatOpen = false
    },
    updateUserCount(userCount) {
      this.userCount = userCount
    }
  }
}
</script>

<style scoped>
.chat-container {
  display: flex;
  max-width: 350px;
  height: 0px;
  flex:1;
  flex-direction: column;
  justify-content: flex-end;
  margin-right: 10px;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
.chat-hidden {
  height: 0px;
  padding-top: 0;
  padding-bottom: 0;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
</style>