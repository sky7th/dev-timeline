<template>
  <div class="chat-container">
    <ChatBottom
      :selectedChatRoom="selectedChatRoom"
      @changeChatOpenState="changeChatOpenState"/>
    <ChatRoom 
      :class="{ 'chat-hidden': !isChatOpen }"/>
  </div>
</template>

<script>
// import { mapGetters, mapActions } from "vuex";
// import notification from '@/libs/notification';
import ChatBottom from '@/components/chat/ChatBottom';
import ChatRoom from '@/components/chat/ChatRoom';

export default {
  props: {
    selectedChatRoom: { type: Object, default: () => ({
      roomId: '',
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
      isChatOpen: true
    }
  },
  methods: {
    changeChatOpenState() {
      if (this.isChatOpen == false)
        this.isChatOpen = true
      else
        this.isChatOpen = false
    }
  }
}
</script>

<style scoped>
.chat-container {
  display: flex;
  max-width: 350px;
  height: auto;
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