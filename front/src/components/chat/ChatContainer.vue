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
  width: 350px;
  min-width: 350px;
  height: 30px;
  flex-direction: column;
  justify-content: flex-end;
  margin-right: 10px;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
.chat-container:nth-child(1) {
  grid-area: 2 / 1;
  z-index: 1;
}
.chat-container:nth-child(2) {
  grid-area: 2 / 2;
  z-index: 1;
}
.chat-container:nth-child(3) {
  grid-area: 2 / 3;
  z-index: 1;
}
.chat-container:nth-child(4) {
  grid-area: 1 / 1;
}
.chat-container:nth-child(5) {
  grid-area: 1 / 2;
}
.chat-container:nth-child(6) {
  grid-area: 1 / 3;
}
.chat-hidden {
  height: 0px;
  padding-top: 0;
  padding-bottom: 0;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}

@media screen and (max-width: 1280px) {
  .chat-container:nth-child(1) {
    grid-area: 3 / 1;
    z-index: 2;
  }
  .chat-container:nth-child(2) {
    grid-area: 3 / 2;
    z-index: 2;
  }
  .chat-container:nth-child(3) {
    grid-area: 2 / 1;
    z-index: 1;
  }
  .chat-container:nth-child(4) {
    grid-area: 2 / 2;
    z-index: 1;
  }
  .chat-container:nth-child(5) {
    grid-area: 1 / 1;
  }
  .chat-container:nth-child(6) {
    grid-area: 1 / 2;
  }
}
@media screen and (max-width: 830px) {
  .chat-container:nth-child(1) {
    grid-area: 6 / 1;
    z-index: 5;
  }
  .chat-container:nth-child(2) {
    grid-area: 5 / 1;
    z-index: 4;
  }
  .chat-container:nth-child(3) {
    grid-area: 4 / 1;
    z-index: 3;
  }
  .chat-container:nth-child(4) {
    grid-area: 3 / 1;
    z-index: 2;
  }
  .chat-container:nth-child(5) {
    grid-area: 2 / 1;
    z-index: 1;
  }
  .chat-container:nth-child(6) {
    grid-area: 1 / 1;
  }
}
@media screen and (max-width: 480px) {
  .chat-container {
    width: 100%;
    min-width: 100%;
  }
}
</style>