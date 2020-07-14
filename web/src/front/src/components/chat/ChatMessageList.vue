<template>
  <ul id="message-list" ref="messageList">
    <li class="message-item" :class="{ 'user-me': isCurrentUser(msg) }" v-for="(msg, i) in messages" :key="i">
      <div
        v-if="!isNoticeMessage(msg)" 
        class="user-name">{{ msg.sender.name }}</div>
      <div 
        :class="{ 'notice-message': isNoticeMessage(msg) }" 
        class="user-message">{{ msg.message }}</div>
    </li>
  </ul>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  props: ['messages'],
  updated() {
    this.scrollDown();
  },
  computed: {
    ...mapGetters(['currentUser', 'token'])
  },
  methods: {
    scrollDown() {
      let element = this.$refs.messageList;
      if (Math.abs(element.scrollTop + element.clientHeight - element.scrollHeight) < 60) {
        element.scrollTop = element.scrollHeight;
      }
    },
    isNoticeMessage(msg) {
      return msg.sender.name === 'NOTICE'
    },
    isCurrentUser(msg) {
      return this.currentUser.id == msg.sender.id
    }
  }
}
</script>

<style scoped>
.message-item {
  display: flex;
  flex-direction: column;
}
.user-name {
  font-size: 13px;
  margin: 6px 0 3px 0;
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
.user-message {
  word-break: break-all;
  max-width: 200px;
  background-color: #eeeeee;
  border-radius: 5px;
  padding: 5px 6px;
  margin-left: 2px;
  margin-bottom: 4px;
  width: fit-content;
  font-size: 14px;
  line-height: 130%;
  white-space: pre;
}
.notice-message {
  width: 100%;
  max-width: none;
  text-align: center;
  background-color: white;
  font-size: 12px;
}
.user-me {
  align-items: flex-end;
}
::-webkit-scrollbar { width: 3.2px; } /* 스크롤 바 */
::-webkit-scrollbar-track { background-color:#f7f7f7; } /* 스크롤 바 밑의 배경 */
::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
</style>