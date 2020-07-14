<template>
  <div id="home">
    <Header/>
    <div class="wrap">
      <SideMenu/>
      <Content/>
    </div>
    <Modal v-if="modalState"/>
    <ClickMenu />
  </div>
</template>

<script>
import Header from '@/components/Header';
import Content from '@/components/Content';
import SideMenu from '@/components/SideMenu';
import { mapGetters } from "vuex";
import Modal from '@/components/modal/Modal'
import ClickMenu from '@/components/clickMenu/ClickMenu';

export default {
  components: {
    Header,
    Content,
    SideMenu,
    Modal,
    ClickMenu
  },
  computed: {
    ...mapGetters(['modalState'])
  }
};
</script>

<style>
#home {
  height: 100%;
}
.wrap {
  display: flex;
}
</style>