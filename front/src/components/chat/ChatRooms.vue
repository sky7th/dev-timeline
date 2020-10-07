<template>
  <div class="chat-rooms"  :class="{ 'chat-rooms-hidden': !isOnChatRooms }">
    <div class="top">
      <font-awesome-icon :icon="['fas', 'arrow-right']" class="close-btn icon-btn" v-on:click="close()"/>
      <font-awesome-icon :icon="['fas', 'sync-alt']" class="refresh-btn icon-btn" v-on:click="refresh()"/>
      <!-- <div class="top-title">채팅방 목록</div> -->
    </div>
    <ul>
      <li
      v-for="({id, name, userCount}) in chatRooms" 
        :key="id"
        @click="enter(id)"
        :class="{ 'selected-room': isEntered(id) }" 
      >
        <div class="left">
          <div class="title">
            {{name}} 
          </div>
          <div class="bottom">
            <div class="user-count">
              {{userCount}} 명
            </div>
          </div>
        </div>
        <img class="logo" src="http://wiki.hash.kr/images/thumb/8/8f/%EC%8A%A4%ED%94%84%EB%A7%81_%EB%A1%9C%EA%B3%A0.png/300px-%EC%8A%A4%ED%94%84%EB%A7%81_%EB%A1%9C%EA%B3%A0.png" alt="">
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
    ...mapActions(['insertSelectedChatRooms', 'updateChatRooms', 'updateIsOnChatRooms']),

    enter(id) {
      if (this.currentUser == null || this.currentUser == undefined) {
        notification.warn('로그인 후 이용 가능합니다.');
        return;
      }
      
      const chatRoom = this.findRoom(id);

      if (chatRoom != null) {
        this.insertSelectedChatRooms(chatRoom);
        // localStorage.setItem('wschat.room', JSON.stringify(this.chatRoom));
      }
    },

    findRoom(id) {
      if (this.selectedChatRooms.find(chatRoom => chatRoom.id === id)) {
        notification.warn('이미 참여중인 채팅방 입니다.');
        return null;
      }
      return this.chatRooms.find(chatRoom => chatRoom.id === id);
    },

    isEntered(id) {
      return this.selectedChatRooms.find(selectedChatRoom => selectedChatRoom.id === id);
    },

    async findRooms() {
      const response = await this.axios.get(`${process.env.VUE_APP_CHAT_API}/chat/rooms`)
        .catch(e => {
          if (e.message === 'Network Error')
            notification.warn('채팅 서버가 꺼져있습니다.');
            this.close();
        })
      return response.data;
    },

    async refresh() {
      const rooms = await this.findRooms();
      this.updateChatRooms(rooms);
    },

    close() {
      this.updateIsOnChatRooms(false);
    }
  }
}
</script>

<style scoped>
.top {
  background-color: #e6e6e6;
  display: flex;
  text-align: center;
  padding: 10px;
  justify-content: center;
  align-items: center;
  height: 30px;
}
.refresh-btn {
  position: absolute;
  right: 14px;
  color: #1E2733;
  cursor: pointer;
}
.close-btn {
  position: absolute;
  left: 14px;
  color: #1E2733;
  cursor: pointer;
}
.icon-btn:hover {
  font-size: 22px;
  /* bottom: 5px; */
  transition: all .1s ease-in;
}
.top-title {

}
.chat-rooms {
  position: fixed;
  bottom: 0px;
  right: 0;
  padding: 57px 0px 0px 0px;
  border-bottom: 1px dashed black;
  width: 350px;
  height: auto;
  height: 100%;
  background-color: #fff;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
.chat-rooms-hidden {
  margin-right: -350px;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
ul {
  width: 100%;
}
li {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 10px 0px;
  background-color: #fff;
  cursor: pointer;
  color: #1E2733;
  border-bottom: 1px solid #ddd;
}
li:hover {
  background-color: #ddd;
  transition: background-color .1s;
  background-color: #f4f4f4;
}
.selected-room {
  background-color: #f4f4f4;
}
.logo {
  height: 40px;
  background: white;
  border-radius: 10px;
  padding: 4px;
  margin-left: 10px;
}
.left {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-end;
    padding: 9px 0px 9px 5px;
}
.title {
  font-size: 15px;
  margin-bottom: 5px;
  font-weight: bold;
}
.user-count {
  font-size: 12px;
  color: #888;
}
</style>