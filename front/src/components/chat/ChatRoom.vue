<template>
  <div class="chat-room" v-cloak>
    <button class="btn-close" @click="unsubscribe">x</button>
    <ChatMessageList :messages="messages" @scrollDown="scrollDown" ref="messageList" />
    <div class="no-connect-wrapper" v-if="!isFirstConnect && !chatConnect.connected">
      <div class="no-connect-message">연결되지 않았습니다.</div>
      <div class="re-connect" @click="initChatConnect(room.id)">다시 연결</div>
    </div>
    <div class="loading-wrapper">
        <img src="../../assets/images/loading.gif" class="loading" ref="spinner"/>
    </div>
    <div class="bottom" v-if="isLogined">
      <textarea type="text" class="form-text" placeholder="내용을 입력해주세요..."
        v-model="message" 
        v-on:keydown.enter="sendMessage">
      </textarea>
      <button class="btn-send" type="button" @click="sendMessage">전송</button>
    </div>
    <div class="bottom" v-else>
      <textarea type="text" class="form-text" placeholder="로그인 후에 채팅방 입장이 가능합니다."
        v-model="message" 
        v-on:keydown.enter="sendMessage" disabled>
      </textarea>
      <button class="btn-send" type="button" @click="login">로그인</button>
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
      start: 0,
      isFirstConnect: true
    }
  },
  mounted() {
    this.messageElement = this.$refs.messageList.$refs.messageList;
    this.spinner = this.$refs.spinner;
    reverseScrollFetch(() => this.insertMessages(), this.messageElement);
    this.subscribe(this.room.id);
  },
  computed: {
    ...mapGetters(['currentUser', 'token', 'currentUser', 'chatConnect', 'selectedChatRooms', 'isLogined']),
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
    ...mapActions(['removeSelectedChatRoom', 'updateChatConnect', 'updateModalContent', 'onModalState']),
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

      this.pushMessage('TALK', this.gerSender(), this.message);
      this.message = '';
    },

    pushMessage(type, sender, message, headers={}) {
      this.chatConnect.ws.send(`/pub/chat/rooms/messages`, headers, JSON.stringify({
        type: type, 
        sender: sender,
        message: message, 
        roomId: this.room.id
      }));
    },

    gerSender() {
      return { userId: this.currentUser.id, name: this.currentUser.name, imageUrl: this.currentUser.imageUrl };
    },

    recvMessage(recv) {
      this.handlerUpdateUserCount(recv.userCount);
      if (recv.type === 'MULTIPLE') {
        return;
      }

      if (this.messages.length === 0 || !this.isSameDate(recv.createdDate, this.messages[this.messages.length - 1].createdDate)) {
        this.messages.push({"type": "DATE", "sender":recv.sender, "message": this.convertDate(recv.createdDate), "createdDate": recv.createdDate})
      }
      
      this.messages.push({"type":recv.type, "sender":recv.sender, "message":recv.message, "createdDate": recv.createdDate});
    },

    recvMessages(messages, isReverse=false) {
      messages = messages.filter(m => m.type !== 'MULTIPLE');
      if (isReverse) {
        messages = messages.reverse();
      }

      messages.forEach(message => {
        if (message.type === 'MULTIPLE') {
          return;
        }

        if (this.messages.length !== 0 && this.messages[0].type === 'DATE' && this.isSameDate(message.createdDate, this.messages[0].createdDate)) {
          this.messages.shift();
        }

        this.messages.unshift(message);
        this.messages.unshift({"type": "DATE", "sender":message.sender, "message": this.convertDate(message.createdDate), "createdDate": message.createdDate});
      });
      // this.messages = [...messages, ...this.messages];
    },

    subscribe(roomId) {
      if (!this.chatConnect.connected) {
        this.initChatConnect();
      } else {
        this.subscribeActionCurried(roomId)();
      }
    },

    startSpinner() {
      this.spinner.style.display = 'block';
      this.spinner.style.visibility = 'visible';
    },

    stopSpinner() {
      this.spinner.style.visibility = 'hidden';
    },

    initChatConnect() {
      this.startSpinner();

      const sock = new SockJS(`${process.env.VUE_APP_CHAT_API}/ws-stomp`);
      this.updateChatConnect({
        connected: false,
        sock: sock,
        ws: this.Stomp.over(sock, { Authorization: `Bearer ${this.token}` }),
        reconnect: 0
      });

      this.chatConnect.ws.connect({}, () => {
        this.chatConnect.connected = true;
        this.isFirstConnect = false;
        this.stopSpinner();

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
        this.startSpinner();

        await this.axios.get(`${process.env.VUE_APP_CHAT_API}/chat/rooms/${roomId}/messages/first`)
          .then(({ data }) => {
            this.recvMessages(data, true);

          }).catch(() => {
              notification.warn('최근 채팅 목록을 불러오지 못했습니다.');
          }).finally(() => this.stopSpinner());

        await this.insertMessages(true);
        this.scrollDown(true);

        this.subscribeObject = this.chatConnect.ws.subscribe(`/sub/chat/rooms/${roomId}`, (message) => {
          var recv = JSON.parse(message.body);
          this.recvMessage(recv);
        }, { id: roomId });

        this.pushMessage('ENTER', null, '', { type: 'ENTER', id: this.room.id });
      }
    },

    async insertMessages(isFirst=false) {
      this.startSpinner();
      await this.axios.get(`${process.env.VUE_APP_CHAT_API}/chat/rooms/${this.room.id}/messages?start=${this.start}&page=${this.page}`)
        .then(({ data }) => {
          if (data.messages.length === 0) {
            notification.success('채팅 메시지를 모두 확인했어요.');
            return;
          }
          
          this.recvMessages(data.messages);
            
          if (isFirst) {
            this.start = this.messages[1].id - 1;
          } else {
            this.page += 1;
          }

        }).catch(() => {
            notification.warn('과거 채팅 목록을 불러오지 못했습니다.');
        }).finally(() => this.stopSpinner());
    },

    connectFailCallback() {
      this.chatConnect.connected = false;
      notification.warn('연결에 실패했습니다.');
      this.stopSpinner();
    },

    unsubscribe() {
      if (this.selectedChatRooms.length === 0) {
        return;
      }

      this.removeSelectedChatRoom(this.room.id);

      if (this.subscribeObject == null) {
        return;
      }

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
    },

    login() {
      this.updateModalContent('LOGIN');
      this.onModalState();
    },

    isSameDate(datetime1, datetime2) {
      return datetime1.split(' ')[0] === datetime2.split(' ')[0];
    },

    convertDate(datetime) {
      const date = datetime.split(' ')[0].replaceAll('-', '. ');
      const dateObj = new Date(date);
      const week = ['일', '월', '화', '수', '목', '금', '토'];
      const dayOfWeek = week[dateObj.getDay()];
      const todayDateObj = new Date();
      const todayDate = `${todayDateObj.getFullYear()}. ${todayDateObj.getMonth() + 1}. ${todayDateObj.getDate()}`;

      return `${date} ${dayOfWeek} ${date === todayDate ? ' - 오늘' : ''}`;
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
  z-index: 0;
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
  height: 60px;
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
.bottom .form-text::placeholder {
  line-height: 160%;
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
  width: 98%;
  height: 100%;
  background-color: white;
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: center;
  align-items: center;
  animation: fadeIn 3s ease-in-out;
  z-index: 1;
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
.loading-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  text-align: center;
  position: absolute;
  width: 100%;
  height: 90%;
}
.loading {
  display: none;
  height: 50px;
  margin-top: -10px;
}

@media screen and (max-width: 480px) {
  .chat-room {
    height: 71vh;
  }
  .top {
    height: 37px;
  }
}

::-webkit-scrollbar { width: 3.2px; } /* 스크롤 바 */
::-webkit-scrollbar-track { background-color:#f7f7f7; } /* 스크롤 바 밑의 배경 */
::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
</style>