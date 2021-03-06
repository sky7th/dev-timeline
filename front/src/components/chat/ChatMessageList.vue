<template>
  <ul id="message-list" ref="messageList">
    <li class="message-item" 
      :class="{ 'user-me': isCurrentUser(msg), 'alert-message': isNoticeMessage(msg), 'date-message-item': isDateMessage(msg) }" 
      v-for="(msg, i) in messages" :key="i"
    >
      <img v-if="!isNoticeMessage(msg)" class="img-user" :src="msg.sender.imageUrl" alt="">
      <div>
        <div
          v-if="!isNoticeMessage(msg)" 
          class="user-name">{{ msg.sender.name }}</div>
        <div class="user-message-wrapper" 
          :class="{ 'user-me-message': isCurrentUser(msg), 'date-message': isDateMessage(msg) }"
        >
          <div 
            :class="{ 'notice-message': isNoticeMessage(msg), 'user-message': !isNoticeMessage(msg) }">{{ msg.message }}</div>
          <div v-if="!isNoticeMessage(msg)" class="date">{{ convertDate(msg.createdDate) }}</div>
        </div>
      </div>
    </li>
  </ul>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  props: ['messages'],
  data() {
    return {
      beforeScrollDiff: 0,
      beforeScrollTop: 0,
      beforeScrollHeight: 0,
      messageElement: null
    }
  },
  updated() {
    this.changeScroll();
  },
  computed: {
    ...mapGetters(['currentUser', 'token', 'isLogined'])
  },
  mounted() {
    this.messageElement = this.$refs.messageList;
  },
  methods: {
    changeScroll() {
      let element = this.messageElement;
      let _scrollDiff = Math.abs(element.scrollTop + element.clientHeight - element.scrollHeight);
      let _scrollHeight = element.scrollHeight
      let _scrollTop = element.scrollTop;

      if (_scrollDiff < 300) {
        element.scrollTop = element.scrollHeight;
      }

      if (_scrollTop < 50 && this.beforeScrollHeight < _scrollHeight) {
        element.scrollTop = _scrollHeight - this.beforeScrollHeight;
      }

      this.beforeScrollHeight = _scrollHeight;
    },

    isNoticeMessage(msg) {
      return msg.type === 'ENTER' || msg.type === 'QUIT' || msg.type === 'DATE'
    },

    isDateMessage(msg) {
      return msg.type === 'DATE'
    },

    isCurrentUser(msg) {
      return msg.sender != null && (this.isLogined && this.currentUser.id === msg.sender.userId);
    },
    
    convertDate(date) {
      let HHmm = date.substr(11, 5);
      let [hour, min] = HHmm.split(':');
      let pre = '오전';
      if (hour > 12) {
        pre = '오후';
        hour = String(Number(hour) - 12)
      }
      if (hour.length == 2 && hour[0] === '0') {
        hour = hour.substr(1, 1);
      }
      return pre + ' ' + hour + ':' + min;
    }
  }
}
</script>

<style scoped>
#message-list {
  z-index: 1;
}
.message-item {
  display: flex;
  flex-direction: row;
  margin-top: 3px;
}
.user-name {
  font-size: 11px;
  margin: 6px 4px 4px 4px;
}
ul {
  overflow-y: scroll;
  height: 100%;
  margin-bottom: 5px;
  padding: 0 6px 0 1px;
}
li {
  list-style: none;
}
.user-message-wrapper {
  display: flex;
  flex-direction: row;
  align-items: flex-end;
}
.user-message {
  word-break: break-all;
  max-width: 200px;
  background-color: #eeeeee;
  border-radius: 5px;
  padding: 5px 8px;
  margin: 0 2px 4px 2px;
  width: fit-content;
  font-size: 13px;
  line-height: 130%;
  white-space: break-spaces;
  /* color: #24292e */
}
.notice-message {
  width: 100%;
  max-width: none;
  text-align: center;
  background-color: white;
  font-size: 12px;
}
.alert-message {
  margin-top: 23px;
}
.alert-message + .alert-message {
  margin-top: 14px;
  margin-bottom: 16px;
}
.message-item:nth-child(1) {
  margin-top: 8px;
}
.user-me {
  align-items: flex-start;
  flex-direction: row-reverse;
  text-align: end;
}
.user-me-message {
  flex-direction: row-reverse;
}
.img-user {
  height: 23px;
  width: 23px;
  border-radius: 5px;
  margin: 6px 3px 0 3px;
}
.date {
  font-size: 10px;
  color: #24292e;
  margin: 0 4px 5px 4px;
}
.alert-message {
  justify-content: center;
}
.date-message-item {
  position: sticky;
  top: 0;
}
.date-message {
  border: 1px solid #aaa;
  border-radius: 20px;
  padding: 3px 8px;
  background-color: white;
  font-weight: bold;
  color: #777;
  margin-top: 2px;
}
::-webkit-scrollbar { width: 3.2px; } /* 스크롤 바 */
::-webkit-scrollbar-track { background-color:#f7f7f7; } /* 스크롤 바 밑의 배경 */
::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
</style>