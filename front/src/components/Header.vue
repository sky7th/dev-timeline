<template>
  <div class="header">
    <notifications group="notify" position="bottom center"/>
    <div class="logo-name">
      <a href="/">dev-time</a>
      <span v-if="isClickedMyLike" class="logo-sub-name">보관함</span>
    </div>
    <SearchBar/>
    <button v-if="!isLogined" @click="login()" class="btn-login">
        <font-awesome-icon :icon="['fas', 'user']" class="user-icon"/>
    </button>
    <button v-else @click="onClick" class="btn-login">
        <img v-if="currentUser" class="login-user-img" :src="currentUser.imageUrl" alt=""> 
        <img v-else class="login-user-img" alt="">
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
    ...mapGetters(['token', 'currentUser', 'isClickedClickMenu', 'isClickedMyLike', 'isLogined'])
  },
  methods: {
    ...mapActions(['setToken', 'setCurrentUser', 'updateIsClickedClickMenu', 
      'updateClickMenuList', 'updateClickMenuLocation', 'updateIsClickedMyLike', 'resetAll',
      'onModalState', 'updateModalContent']),
    logout() {
      this.setToken(null)
      this.setCurrentUser(null)
      this.$router.replace('/');
      location.reload()
      notification.success('로그아웃 처리 되었습니다.')
    },
    login() {
      // this.$router.replace('/login');
      this.updateModalContent('LOGIN');
      this.onModalState();
    },
    onClick(event) {
      event.stopPropagation();
      if (this.isClickedClickMenu) {
        this.updateIsClickedClickMenu(false);
        this.updateClickMenuList([]);
        this.updateClickMenuLocation([])
      } 
      else {
        this.updateIsClickedClickMenu(true);
        let x = event.clientX;
        let y = event.clientY;
        this.updateClickMenuLocation([x - 70, y + 13]);
        this.updateClickMenuList([
        { name: '내 정보', func: this.onClickMyAccount },
        { name: '내 친구', func: this.onClickMyFollow },
        { name: this.isClickedMyLike ? '메인으로' : '보관함', func: this.onClickMyLike },
        { name: this.currentUser ? '로그아웃' : '로그인', func: this.onClickLoginOrLogout }
      ]);
      }
      
    },
    onClickMyAccount() {
      event.stopPropagation();
      alert('준비중 입니다 :^)')
    },
    onClickMyFollow() {
      event.stopPropagation();
      alert('준비중 입니다 :^)')
    },
    onClickMyLike() {
      event.stopPropagation();
      if (this.isClickedMyLike) {
        this.updateIsClickedMyLike(false);
      } else {
        this.updateIsClickedMyLike(true);
      }
      this.resetAll();
    },
    onClickLoginOrLogout() {
      event.stopPropagation();
      if (this.currentUser) {
        this.logout();
      }
      else {
        this.login();
      }
    }
  }
}
</script>

<style scoped>
.header {
  position: fixed;
  top: 0;
  width: 100%;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 57px;
  /* border: 1px solid black; */
  z-index: 1000;
}
.logo-name {
  padding-left: 20px;
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
.logo-sub-name {
  font-size: 13px;
  margin-left: 6px;
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