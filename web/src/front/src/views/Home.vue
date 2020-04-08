<template>
  <div id="home">
    <Header/>
    <div class="wrap">
      <SideMenu/>
      <Content/>
    </div>
  </div>
</template>

<script>
import Header from '@/components/Header';
import Content from '@/components/Content';
import SideMenu from '@/components/SideMenu';

export default {
  components: {
    Header,
    Content,
    SideMenu
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