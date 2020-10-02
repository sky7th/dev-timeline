<template>
  <div class="chat-rooms"  :class="{ 'chat-rooms-hidden': !isOnChatRooms }">
    <ul>
      <li
      v-for="({id, name, userCount}) in chatRooms"
        :key="id"
      >
        <button @click="enter(id)">
          {{id}} {{name}} {{userCount}}
        </button>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import notification from '../../libs/notification';

export default {
  computed: {
    ...mapGetters(['chatRooms', 'isOnChatRooms', 'selectedChatRooms', 'currentUser'])
  },
  methods: {    
    ...mapActions(['insertSelectedChatRooms']),
    enter(roodId) {
      console.log(this.currentUser);
      if (this.currentUser == null || this.currentUser == undefined) {
        notification.warn('로그인 후 이용 가능합니다.');
        return;
      }
      
      this.chatRoom = this.findRoom(roodId);
      if (this.chatRoom != null) {
        this.insertSelectedChatRooms(this.chatRoom);
        // localStorage.setItem('wschat.room', JSON.stringify(this.chatRoom));
      }
    },
    findRoom(roomId) {
      if (this.selectedChatRooms.find(chatRoom => chatRoom.id === roomId)) {
        notification.warn('이미 참여중인 채팅방 입니다.');
        return null;
      }
      return this.chatRooms.find(chatRoom => chatRoom.id === roomId);
    },
  }
}
</script>

<style scoped>
.chat-rooms {
  position: fixed;
  bottom: 500px;
  right: 0;
  padding: 7px 5px 4px 5px;
  border-bottom: 1px dashed black;
  display: flex;
  max-width: 350px;
  height: auto;
  flex:1;
  flex-direction: column;
  justify-content: flex-end;
  margin-right: 10px;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
.chat-rooms-hidden {
  margin-right: -50px;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
</style>