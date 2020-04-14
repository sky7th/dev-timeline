<template>
  <div class="header">
    <notifications group="notify" position="bottom center"/>
    <a href="/" class="logo-name">dev-time</a>
    <SearchBar/>
    <button v-if="token==null || token==='null' || token ===''" @click="login()" class="btn-login">로그인</button>
    <button v-else @click="logout()" class="btn-login">로그아웃</button>
  </div>
</template>

<script>
import SearchBar from '@/components/search/SearchBar';
import { mapGetters, mapActions } from "vuex";
import notification from '@/libs/notification';

export default {
  components: {
    SearchBar
  },
  computed: {
    ...mapGetters(['token'])
  },
  methods: {
    ...mapActions(['setToken', 'setUserDetail']),
    logout() {
      this.setToken(null)
      this.setUserDetail(null)
      this.$router.replace('/');
      notification.success('로그아웃 처리 되었습니다.')
    },
    login() {
      this.$router.replace('/login');
    }
  }
}
</script>

<style scoped>
.header {
  position: fixed;
  top: 0;
  width: 100%;
  padding: 0 40px;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 57px;
  /* border: 1px solid black; */
  z-index: 1000;
}
.logo-name {
  font-size: 25px;
  font-weight: bold;
  color: #4E5763;
  cursor: pointer;
}
.btn-login {
  color: #4E5763;
  font-weight: 600;
  font-size: 18px;
  background-color: white;
  border: none;
  cursor: pointer;
  height: 50px;
}
</style>