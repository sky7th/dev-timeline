<template>
  <div class="chat-room" v-cloak>
    <button class="btn-close" @click="unsubscribe">x</button>
    <ChatMessageList :messages="messages" @scrollDown="scrollDown" ref="messageList" />
    <div class="no-connect-wrapper" v-if="!chatConnect.connected">
      <div class="no-connect-message">연결되지 않았습니다.</div>
      <div class="re-connect" @click="initChatConnect(room.id)">다시 연결</div>
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
import { reverseScrollFetch } from '../../utils/scrollFetch';
import notification from '../../libs/notification';
import ChatMessageList from '@/components/chat/ChatMessageList';

const MAXIMUM_CHAT_MESSAGE_SIZE = 1000;

export default {
  components: {
    ChatMessageList
  },
  props: {
    isChatOpen: { type: Boolean, default: true },
    room: { type: Object, default: () => ({
      id: 0,
      roomId: '',
      name: '',
      userCount: 0
    })}
  },
  data() {
    return {
      sender: '',
      message: '',
      messages: [],
      subscribeObject: null,
      page: 0,
      start: 0
    }
  },
  mounted() {
    this.messageElement = this.$refs.messageList.$refs.messageList;
    reverseScrollFetch(() => this.insertMessages(), this.messageElement);
    this.subscribe(this.room.id);
  },
  computed: {
    ...mapGetters(['currentUser', 'token', 'currentUser', 'chatConnect', 'selectedChatRooms']),
    isConnected: function () {
      return this.chatConnect.connected;
    },
  },
  watch: {
    isConnected: function (newVal) {
      if (newVal) {
        this.subscribeActionCurried(this.room.id)();
      }
    }
  },
  methods: {
    ...mapActions(['removeSelectedChatRoom', 'updateChatConnect']),
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
        
      if (!this.chatConnect.ws || !this.chatConnect.ws.connected) {
        this.chatConnect.connected = false;
        return;
      }

      this.chatConnect.ws.send(`/pub/chat/rooms/messages`, {}, JSON.stringify({
        type:'TALK', 
        sender: { userId: this.currentUser.id, name: this.currentUser.name, imageUrl: this.currentUser.imageUrl },
        message: this.message, 
        roomId: this.room.id
      }));
      this.message = '';
    },

    recvMessage(recv) {
      this.handlerUpdateUserCount(recv.userCount);
      if (recv.type === 'MULTIPLE') {
        return;
      }
      this.messages.push({"type":recv.type, "sender":recv.sender, "message":recv.message,
        "createdDate": recv.createdDate});
    },

    recvMessages(messages, isReverse=false) {
      messages = messages.filter(m => m.type !== 'MULTIPLE');
      if (isReverse) {
        messages = messages.reverse();
      }

      this.messages = [...messages, ...this.messages];
    },

    subscribe(roomId) {
      if (!this.chatConnect.connected) {
        this.initChatConnect();
      } else {
        this.subscribeActionCurried(roomId)();
      }
    },

    initChatConnect() {
      const sock = new SockJS(`${process.env.VUE_APP_CHAT_API}/ws-stomp`);
      this.updateChatConnect({
        connected: false,
        sock: sock,
        ws: this.Stomp.over(sock, { Authorization: `Bearer ${this.token}` }),
        reconnect: 0
      });

      this.chatConnect.ws.connect({}, () => {
        this.chatConnect.connected = true;
      }, this.connectFailCallback);
    },

    initChat() {
      this.messages = [];
      this.page = 0;
      this.start = 0;
    },

    subscribeActionCurried(roomId) {
      this.initChat();

      return async () => {
        await this.axios.get(`${process.env.VUE_APP_CHAT_API}/chat/rooms/${roomId}/messages/first`)
          .then(({ data }) => {
            this.recvMessages(data);

          }).catch(() => {
              notification.warn('최근 채팅 목록을 불러오지 못했습니다.');
          });

        this.insertMessages(true);
        this.scrollDown(true);

        this.subscribeObject = this.chatConnect.ws.subscribe(`/sub/chat/rooms/${roomId}`, (message) => {
          var recv = JSON.parse(message.body);
          this.recvMessage(recv);
        }, { id: roomId });
      }
    },

    async insertMessages(isFirst=false) {
      await this.axios.get(`${process.env.VUE_APP_CHAT_API}/chat/rooms/${this.room.id}/messages?start=${this.start}&page=${this.page}`)
        .then(({ data }) => {
          if (data.messages.length === 0) {
            notification.success('채팅 메시지를 모두 확인했어요.');
            return;
          }
          
          this.recvMessages(data.messages, true);
            
          if (isFirst) {
            this.start = this.messages[0].id - 1;
          } else {
            this.page += 1;
          }

        }).catch(() => {
            notification.warn('과거 채팅 목록을 불러오지 못했습니다.');
        });
    },

    connectFailCallback() {
      this.chatConnect.connected = false;
      notification.warn('연결에 실패했습니다.');
    },

    unsubscribe() {
      if (this.selectedChatRooms.length === 0) {
        return;
      }

      this.removeSelectedChatRoom(this.room.id);

      if (this.chatConnect.connected) {
        this.subscribeObject.unsubscribe(this.room.id, {});

        if (this.selectedChatRooms.length === 0) {
          this.chatConnect.ws.disconnect(() => {
            this.chatConnect.connected = false;
          }, {});
        }
      }
    },

    handlerUpdateUserCount(userCount) {
      this.$emit('updateUserCount', userCount)
    },

    isCurrentUser(msg) {
      return this.currentUser.id == msg.sender.id
    },

    scrollDown(isForce=false) {
      let element = this.messageElement;
      if (isForce || Math.abs(element.scrollTop + element.clientHeight - element.scrollHeight) < 300) {
        element.scrollTop = element.scrollHeight;
      }
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
  height: 600px;
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
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: white;
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: center;
  align-items: center;
  animation: fadeIn 3s ease-in-out;
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

@media screen and (max-width: 480px) {
  .chat-room {
    height: 71vh;
  }
}

::-webkit-scrollbar { width: 3.2px; } /* 스크롤 바 */
::-webkit-scrollbar-track { background-color:#f7f7f7; } /* 스크롤 바 밑의 배경 */
::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
</style>