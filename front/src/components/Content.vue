<template>
  <div class="content">
    <TagBar/>
    <RecruitPosts v-if="selectedMenu==='recruit-posts'"/>
    <TechPosts v-else-if="selectedMenu==='tech-posts'"/>
    <LinkPosts v-else-if="selectedMenu==='link-posts'"/>

    <SideButton v-if="isMenuPossibleWrite && currentUser!=null" @event="handlerOnModalState" style="bottom: 130px;"/>
    <ChatButton/>
    <ChatRooms/>
    <div class="chat-bottoms-wrapper">
      <ChatContainer 
        v-for="(room) in selectedChatRooms" 
        :key="room.id" :room=room />
    </div>
    <LoadingContent v-if="isLoadingContent"/>
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
import LoadingContent from '@/components/content/LoadingContent'
import ChatRooms from '@/components/chat/ChatRooms'

import { mapGetters, mapActions } from "vuex";

export default {
  components: {
    RecruitPosts,
    TechPosts,
    LinkPosts,
    TagBar,
    ChatContainer,
    ChatButton,
    SideButton,
    LoadingContent,
    ChatRooms
  },
  computed: {
    ...mapGetters(['selectedMenu', 'selectedChatRooms', 'modalState', 'currentUser', 'isLoadingContent']),
    // isBtnVisible() {
    //   var rooms = this.selectedChatRooms.filter(selectedChatRoom => selectedChatRoom.name === this.selectedMenu)
    //   if (rooms.length === 0)
    //     return true;
    //   else
    //     return false; 
    // },
    isMenuPossibleWrite() {
      if (this.selectedMenu === 'link-posts') 
        return true;
      else
        return false;
    }
  },
  methods: {
    ...mapActions(['onModalState', 'updatePostState', 'updateModalContent']),
    handlerOnModalState() {
      this.updateModalContent('LINK')
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
  display: grid;
  grid-template-rows: repeat(2, 30px);
  grid-template-columns: repeat(3, 370px);
  width: 85%;
  min-width: 85%;
  justify-content: center;
}
@media screen and (max-width: 1280px) {
  .chat-bottoms-wrapper {
    grid-template-rows: repeat(3, 30px);
    grid-template-columns: repeat(2, 370px);
    width: 85%;
    min-width: 85%;
    justify-content: center;
  }
}
@media screen and (max-width: 830px) {
  .chat-bottoms-wrapper {
    left: 55px;
    grid-template-rows: repeat(6, 30px);
    grid-template-columns: repeat(1, 370px);
    width: 85%;
    min-width: 85%;
    justify-content: center;
  }
}
@media screen and (max-width: 480px) {
    .content {
        padding-left: 0;
    }
    .chat-bottoms-wrapper {
      grid-template-rows: repeat(6, 30px);
      grid-template-columns: repeat(1, 100%);
      left: 0px;
      width: 100%;
      min-width: 100%;
    }
}
</style>