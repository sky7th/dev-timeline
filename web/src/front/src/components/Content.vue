<template>
  <div class="content">
    <TagBar/>
    <RecruitPosts v-if="selectedMenu==='recruit-posts'"/>
    <TechPosts v-else-if="selectedMenu==='tech-posts'"/>
    <LinkPosts v-else-if="selectedMenu==='link-posts'"/>

    <SideButton v-if="isMenuPossibleWrite && currentUser!=null" @event="handlerOnModalState" style="bottom: 50%;"/>
    <ChatButton v-if="isBtnVisible"/>
    <div class="chat-bottoms-wrapper">
      <ChatContainer 
        :selectedChatRoom="selectedChatRoom" 
        v-for="(selectedChatRoom) in selectedChatRooms" 
        :key="selectedChatRoom.name"/>
    </div>
  </div>
</template>

<script>
import RecruitPosts from '@/components/content/RecruitPosts';
import TechPosts from '@/components/content/TechPosts';
import LinkPosts from '@/components/content/LinkPosts';
import TagBar from '@/components/search/TagBar';
import ChatContainer from '@/components/chat/ChatContainer';
import ChatButton from '@/components/chat/ChatButton';
import SideButton from '@/components/common/button/SideButton';
import Constant from '@/constant/Constant'

import { mapGetters, mapActions } from "vuex";

export default {
  components: {
    RecruitPosts,
    TechPosts,
    LinkPosts,
    TagBar,
    ChatContainer,
    ChatButton,
    SideButton
  },
  computed: {
    ...mapGetters(['selectedMenu', 'selectedChatRooms', 'selectedChatRooms', 'modalState', 'currentUser']),
    isBtnVisible() {
      var rooms = this.selectedChatRooms.filter(selectedChatRoom => selectedChatRoom.name === this.selectedMenu)
      if (rooms.length === 0)
        return true;
      else
        return false; 
    },
    isMenuPossibleWrite() {
      if (this.selectedMenu === 'link-posts') 
        return true;
      else
        return false;
    }
  },
  methods: {
    ...mapActions(['onModalState', 'updatePostState']),
    handlerOnModalState() {
      this.updatePostState(Constant.CREATE);
      this.onModalState();
    }
  }
}
</script>

<style scoped>
.content {
  height: auto;
  width: 100%;
  height: auto;
  min-height: 100%;
  position: absolute;
  box-sizing: border-box;
  padding-left: 72px;
  padding-top: 57px;
  -webkit-animation: fadeIn 0.3s ease-in-out;
  animation: fadeIn 0.3s ease-in-out;
  z-index: 900;
  background-color: #f7f7f7;
}
.chat-bottoms-wrapper {
  position: fixed;
  bottom: 0;
  left: 100px;
  display: flex;
  width: 85%;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}
</style>