<template>
  <div class="chat-button">
    <notifications group="notify" position="bottom center"/>
    <button class="list-group-item list-group-item-action" v-on:click="enterRoom(chatRoomName)">
      <div style="margin-bottom: 3px;">{{ convertNameSelectedChatRoom() }}</div>
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
    ...mapGetters(['selectedMenu', 'currentUser', 'chatRooms', 'selectedChatRooms']),
    chatRoomName() {
      return this.selectedMenu;
    } 
  },
  methods: {
    ...mapActions(['updateChatRooms', 'insertSelectedChatRooms']),
    async findRooms() {
      const response = await this.axios.get(`${process.env.VUE_APP_CHAT_API}/chat/rooms`)
        .catch(e => {
          if (e.message === 'Network Error')
            notification.warn('채팅 서버가 꺼져있습니다.');
        })
      return response.data.map(room => { return { roomId: room.roomId, name: room.name }});
    },
    findRoomByName(name) {
      return this.chatRooms.find(chatRoom => chatRoom.name === name);
    },
    async createRoom(name) {
      var params = new URLSearchParams();
      params.append("name", name);
      const response = await this.axios.post(`${process.env.VUE_APP_CHAT_API}/chat/rooms`, params);

      return { roomId: response.data.roomId, name: response.data.name };
    },
    async enterRoom(name) {
      if (this.currentUser === null) {
        notification.warn('로그인 후 이용 가능합니다.');
        return;
      }
      if (this.chatRooms.length === 0) {
        const rooms = await this.findRooms();
        this.updateChatRooms(rooms);
      }
      
      this.chatRoom = this.findRoomByName(name);

      if (this.chatRoom === undefined) {
        this.chatRoom = await this.createRoom(this.selectedMenu);
      }

      if (this.chatRoom === '') {
        notification.warn('채팅방이 생성되지 않았습니다.')
      }
      
      this.insertSelectedChatRooms(this.chatRoom);

      var sender = {
        id: this.currentUser.id,
        name: this.currentUser.name,
        imageUrl: this.currentUser.imageUrl
      };

      if(sender.id != "" || sender.id != null || sender.id != undefined) {
          localStorage.setItem('wschat.sender', JSON.stringify(sender));
          localStorage.setItem('wschat.roomId', JSON.stringify(this.chatRoom));
      }
    },
    convertNameSelectedChatRoom() {
      if (this.selectedMenu === 'recruit-posts')
        return '채용';
      else if (this.selectedMenu === 'tech-posts')
        return '테크 블로그';
    }
  }
}
</script>

<style scoped>
.chat-button {
  position: fixed;
  bottom: 50px;
  right: 0;
  z-index: 2000;
}
.chat-button button {
  border: 0px solid black;
  padding: 10px;
  /* border-radius: 100px; */
  cursor: pointer;
  background-color: white;
  font-size: 14px;
  box-shadow: 0 1px 3px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
}
.chat-button button:hover {
  background-color: #cacaca;
  transition: background-color .3s;
}
</style>