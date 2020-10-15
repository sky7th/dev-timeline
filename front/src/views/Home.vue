<template>
  <div id="home">
    <Header/>
    <div class="wrap">
      <SideMenu/>
      <Content/>
    </div>
    <Modal v-if="modalState"/>
    <ClickMenu />
    <!-- <div id="home-spinner" ref="homeSpinner">
        <img src="../assets/images/loading.gif" class="loading" ref="spinner"/>
    </div> -->
    <LoadingContent/>
  </div>
</template>

<script>
import Header from '@/components/Header';
import Content from '@/components/Content';
import SideMenu from '@/components/SideMenu';
import { mapGetters } from "vuex";
import Modal from '@/components/modal/Modal'
import ClickMenu from '@/components/clickMenu/ClickMenu';
import LoadingContent from "@/components/content/LoadingContent";

export default {
  components: {
    Header,
    Content,
    SideMenu,
    Modal,
    ClickMenu,
    LoadingContent
  },
  computed: {
    ...mapGetters(['modalState'])
  },
};
</script>

<style>
#home {
  height: 100%;
}
.wrap {
  display: flex;
}
#home-spinner {
  position: fixed;
  z-index: 9999;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  background-color: rgba(120, 120, 120, 0.4);
  animation: fadeIn 0.3s ease-in-out;
}
#home-spinner img {
  height: 120px;
}
</style>