<template>
  <div class="header">
    <notifications group="notify" position="bottom center"/>
    <a href="/" class="logo-name">dev-time</a>
    <SearchBar/>
    <button v-if="token==null || token==='null' || token ===''" @click="login()" class="btn-login">
        <font-awesome-icon :icon="['fas', 'user']" class="user-icon"/>
    </button>
    <button v-else @click="logout()" class="btn-login">
        <img class="login-user-img" :src="currentUser.imageUrl" alt="">
    </button>
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
    ...mapGetters(['token', 'currentUser'])
  },
  methods: {
    ...mapActions(['setToken', 'setUserDetail']),
    logout() {
      if (!confirm('로그아웃 하시겠습니까?'))
        return;
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
.btn-login:focus {
    outline: none;
}
.user-icon {
    font-size: 25px;
    margin: 0 5px 0 7px;
}
.login-user-img {
    height: 29px;
    width: 29px;
    border-radius: 5px;
    margin: 5px 10px 0 10px;
}
@media screen and (max-width: 480px) {
    .logo-name {
        display: none;
    }
    .header {
        padding: 0 0 0 60px;
    }
}
</style>