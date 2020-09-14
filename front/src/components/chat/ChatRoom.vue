<template>
  <div class="chat-room" v-cloak>
    <button class="btn-close" @click="disconnect">x</button>
    <ChatMessageList v-if="connected" :messages="messages"/>
    <div class="no-connect-wrapper" v-else>
      <div class="no-connect-message">연결되지 않았습니다.</div>
      <div class="re-connect" @click="reConnect">다시 연결</div>
    </div>
    <div class="bottom">
      <textarea type="text" class="form-text" placeholder="내용을 입력해주세요..."
        v-model="message" 
        v-on:keydown.enter="sendMessage">
      </textarea>
      <button class="btn-send" type="button" @click="sendMessage">전송</button>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import notification from '../../libs/notification';
import ChatMessageList from '@/components/chat/ChatMessageList';

const MAXIMUM_CHAT_MESSAGE_SIZE = 1000;

export default {
  components: {
    ChatMessageList
  },
  props: {
    isChatOpen: { type: Boolean, default: true }
  },
  data() {
    return {
      room: {},
      sender: '',
      message: '',
      messages: [],
      connected: false
    }
  },
  computed: {
    ...mapGetters(['currentUser', 'token'])
  },
  created() {
    this.room = JSON.parse(localStorage.getItem('wschat.roomId'));
    this.sender = JSON.parse(localStorage.getItem('wschat.sender'));
    this.connect();
  },
  methods: {
    ...mapActions(['removeSelectedChatRoom']),
    sendMessage(event) {
      event.preventDefault();
      if (event.keyCode == 13 && event.shiftKey) {
        this.message +='\n';
        return;
      }
      if (this.message === '' || this.message === '\n') 
        return;

      if (this.message.length > MAXIMUM_CHAT_MESSAGE_SIZE) {
        notification.warn(`채팅글 길이는 ${MAXIMUM_CHAT_MESSAGE_SIZE}자를 넘을 수 없습니다.`);
        return;
      }
        
      if (!this.ws || !this.ws.connected) {
        this.connected = false;
        return;
      }
      this.ws.send(`/pub/chat/message`, { token: this.token }, JSON.stringify({
        type:'TALK', roomId: this.room.roomId, sender: this.sender, message: this.message
      }));
      this.message = '';
    },
    recvMessage(recv) {
      this.handlerUpdateUserCount(recv.userCount)
      this.messages.push({"type":recv.type, "sender":recv.sender, "message":recv.message,
        "createdDate": recv.createdDate});
    },
    connect() {
      this.connected = true;
      this.sock = new SockJS(`${process.env.VUE_APP_CHAT_API}/ws-stomp`);
      this.ws = Stomp.over(this.sock);
      this.reconnect = 0;

      this.ws.connect({ token: this.token }, () => {
        this.ws.subscribe(`/sub/chat/room/${this.room.roomId}`, (message) => {
          var recv = JSON.parse(message.body);
          this.recvMessage(recv);
        });
        this.ws.send(`/pub/chat/message`, { token: this.token }, JSON.stringify({
        type:'TALK', roomId: this.room.roomId, sender: { id: null, name: 'NOTICE', imageUrl: null }, message: this.sender.name + ' 님이 들어왔습니다.'
      }));
      }, () => {
        this.connected = false;
        notification.warn('연결에 실패했습니다.');

        // if (this.reconnect++ < 5) {
        //   setTimeout(() => {
        //     this.sock = new SockJS(`${process.env.VUE_APP_CHAT_API}/ws-stomp`);
        //     this.ws = Stomp.over(this.sock);
        //     this.connect();
        //   }, 10 * 1000);
        // }
      });
    },
    disconnect() {
      if (this.ws) {
        this.ws.disconnect();
        this.removeSelectedChatRoom(this.room.roomId);
      }
    },
    reConnect() {
      this.connect();
    },
    handlerUpdateUserCount(userCount) {
      this.$emit('updateUserCount', userCount)
    },
    isNoticeMessage(msg) {
      return msg.sender.name === 'NOTICE'
    },
    // isSameUserBeforeMessage(msg) {
    //   if (this.messages.length === 1) {
    //     return false;
    //   }
    //   return this.messages[this.messages.length - 2].sender.id == msg.sender.id;
    // },
    isCurrentUser(msg) {
      return this.currentUser.id == msg.sender.id
    }
  }
}
</script>

<style scoped>
.chat-room {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 500px;
  background-color: white;
  padding: 5px;
  border-radius: 3px;
  box-shadow: 0 1px 2px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
.btn-close {
  position: absolute;
  border: 0px;
  right: 5px;
  font-weight: bold;
  border-radius: 5px;
  background-color: #eaeaea;
  top: -25px;
  cursor: pointer;
}
.btn-close:hover {
  background-color: #aaaaaa;
}
.bottom {
  display: flex;
}
.bottom .form-text {
  height: 52px;
  border: 1.5px solid #dadada;
  border-radius: 4px;
  box-shadow: 0px 0px 1px 0px rgba(0, 0, 0, 0.2) inset;
  padding: 7px 8px;
  word-break: break-all;
  background-color: #f2f2f2;
  resize: none;
  font-size: 13px;
  flex: 6;
}
.bottom .btn-send {
  flex: 1;
  background-color: #fafafa;
  border: 1.5px solid #e8e8e8;
  border-radius: 4px;
  box-shadow: 0px 0px 1px 0px rgba(0, 0, 0, 0.2) inset;
  font-size: 13px;
  cursor: pointer;
}
.bottom .btn-send:hover {
  background-color: #eaeaea;
}
.no-connect-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: center;
  align-items: center;
}
.no-connect-message {
  font-size: 14px;
  margin-bottom: 20px;
}
.re-connect {
  font-size: 14px;
  border: 1px solid #aaaaaa;
  border-radius: 3px;
  padding: 4px 6px;
  cursor: pointer;
}
.re-connect:hover {
  background-color: #eaeaea;
}
.no-user-name {
  display: none;
}
::-webkit-scrollbar { width: 3.2px; } /* 스크롤 바 */
::-webkit-scrollbar-track { background-color:#f7f7f7; } /* 스크롤 바 밑의 배경 */
::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
</style>