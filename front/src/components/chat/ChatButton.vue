<template>
  <div class="chat-button">
    <notifications group="notify" position="bottom center"/>
    <button class="list-group-item list-group-item-action" v-on:click="open()">
      <div>채팅방</div>
    </button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import notification from '@/libs/notification';

export default {
  data() {
    return {
      chatRoom: ''
    }
  },
  computed: {
    ...mapGetters(['selectedMenu', 'currentUser', 'chatRooms', 'selectedChatRooms', 'isOnChatRooms']),
    chatRoomName() {
      return this.selectedMenu;
    } 
  },
  methods: {
    ...mapActions(['updateChatRooms', 'insertSelectedChatRooms', 'updateIsOnChatRooms']),
    async findRooms() {
      const response = await this.axios.get(`${process.env.VUE_APP_CHAT_API}/chat/rooms`)
        .catch(e => {
          if (e.message === 'Network Error')
            notification.warn('채팅 서버가 꺼져있습니다.');
        })
      return response.data;
    },

    async open() {
      if (!this.isOnChatRooms) {
        const rooms = await this.findRooms();
        this.updateChatRooms(rooms);
        this.updateIsOnChatRooms(true);

      } else {
        this.updateIsOnChatRooms(false);
      }
    },
  }
}
</script>

<style scoped>
.chat-button {
  position: fixed;
  bottom: 80px;
  right: 0;
  animation: fadeIn 0.3s ease-in-out;
}
.chat-button button {
  border: 0px solid black;
  padding: 10px;
  /* border-radius: 100px; */
  cursor: pointer;
  background-color: white;
  font-size: 13px;
  font-weight: 500;
  box-shadow: 0 1px 3px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
}
.chat-button button:hover {
  background-color: #cacaca;
  transition: background-color .3s;
}
</style>