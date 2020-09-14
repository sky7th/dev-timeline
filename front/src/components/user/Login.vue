<template>
  <div class="login-container">
    <notifications group="notify" position="bottom center"/>
      <div class="login-content">
          <h1 class="login-title">dev-time</h1>
          <form @submit.prevent="login">
              <input type="email" name="email"
                      class="form" placeholder="이메일"
                      v-model="user.email"/>
              <input type="password" name="password"
                      class="form" placeholder="비밀번호"
                      v-model="user.password"/>
              <button type="submit" class="btn-login">로그인</button>
          </form>
          <span class="signup-link" @click="updateModalContent('SIGNUP')">회원가입</span>
          <div class="middle">
              <!-- 소셜 계정으로 로그인 하시겠어요 ? -->
          </div>
          <div class="social-login">
              <!-- <a class="social-btn google" :href="getOauthUrl('google')">
                  <img src="../../assets/images/google-logo.png" alt="Google" /> 
                  <div>Google 로그인</div>
              </a> -->
              <a class="social-btn github" :href="getOauthUrl('github')">
                    <img src="../../assets/images/github-logo.png" alt="Github" /> 
                    <div>Github 로그인</div>
              </a>
          </div>
          <!-- <span class="main-link"><router-link to="/">홈으로 이동</router-link></span> -->
      </div>
  </div>
</template>

<script>
import notification from '@/libs/notification';
import { mapActions } from "vuex";

export default {
  name: "login",
  data: () => ({
    user: {
      email: '',
      password: '',
    }
  }),
  methods: {
    ...mapActions(['updateModalContent']),
    async login() {
      if (this.user.email === '') {
        notification.warn('이메일을 입력해주세요.')
        return
      }
      if (this.user.password === '') {
        notification.warn('비밀번호를 입력해주세요.')
        return
      }
      this.axios.post('/auth/login', this.user)
      .then(response => {
        notification.success('로그인 성공', () => {
          this.$store.commit('setToken', response.data.accessToken);
          this.$emit('getUserDetails');
          location.reload();
        });
      })
      .catch(err => {
        if (err.response.data.message === 'Bad credentials') 
          notification.warn('이메일 혹은 비밀번호가 옳지 않습니다.')
        else {
          notification.warn('비어있는 항목이 존재합니다.')
        }
      })
    },
    getOauthUrl(platfrom) {
      return `${process.env.VUE_APP_API}/oauth2/authorize/${platfrom}?redirect_uri=${process.env.VUE_APP_ORIGIN}${process.env.VUE_APP_OAUTH2_REDIRECT_URI}`
    }
  }
}
</script>

<style scoped>
.social-login {
  display: flex;
  flex-direction: column;
}
.social-login a {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  padding: 10px 20px;
  margin-bottom: 15px;
  border-radius: 10px;
  min-height: 40px;
  width: 270px;
  box-shadow: 0 1px 4px rgba(27,31,35,.2);
}
.social-login a:hover {
  box-shadow: 0 1px 6px rgba(27,31,35,.3);
}
.social-login a div {
  flex: 1;
  text-align: center;
  font-size: 15px;
  color: #5a5a5a;
}
.login-container {
  display: flex;
  justify-content: center;
  animation: fadeIn 0.3s ease-in-out;
}
.login-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #fff;
  border-radius: 3px;
  box-shadow: 0 1px 2px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
  width: 500px;
  padding: 35px 35px;
}
.login-content form {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 13px;
}
.social-btn {
  margin-bottom: 15px;
  font-weight: 400;
  font-size: 16px;
}
.social-btn img {
  height: 32px;
  margin-right: 15px;
}
.social-btn.google {
    margin-top: 7px;
}
.social-btn.facebook img {
    height: 24px;
    margin-left: 3px;
    margin-top: 3px;
    margin-bottom: 3px;
}
.social-btn.github img {
    height: 24px;
    margin-left: 3px;
}
.middle {
  font-size: 14px;
  margin: 30px 0 10px 0;
  color: #5a5a5a;
}
.form {
  display: block;
  height: 41px;
  width: 300px;
  border: 1px solid #bababa;
  border-radius: 3px;
  box-shadow: 0px 0px 2px 0px rgba(0, 0, 0, 0.2) inset;
  margin-bottom: 10px;
  padding: 0 11px;
}
.btn-login {
  height: 38px;
  width: 300px;
  color: white;
  font-weight: 600;
  margin-left: -2px;
  margin-top: 5px;
  background-color: #2d92f1;
  font-size: 15px;
  border: 1px solid #bababa;
  border-radius: 3px;
  box-shadow: 0px 0px 1px 0px rgba(0, 0, 0, 0.2) inset;
  cursor: pointer;
}
.btn-login:hover {
  background-color: #0d72d1;
}
.signup-link {
  color: rgba(0, 0, 0, 0.65);
  font-size: 14px;
  cursor: pointer;
}
.login-title {
  font-size: 30px;
  font-weight: bold;
  color: #4E5763;
  margin-top: 0;
  margin-bottom: 45px;
}
.main-link {
  margin-top: 30px;
  color: rgba(0, 0, 0, 0.65);
  font-size: 15px;
  cursor: pointer;
}
</style>