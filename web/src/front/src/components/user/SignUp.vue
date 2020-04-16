<template>
    <div class="signup-container">
      <notifications group="notify" position="bottom center"/>
        <div class="signup-content">
            <h1 class="signup-title">S i g n  U p</h1>
            <form @submit.prevent="signup">
                <input type="text" name="name"
                        class="form" placeholder="이름"
                        v-model="user.name"/>
                <input type="email" name="email"
                        class="form" placeholder="이메일"
                        v-model="user.email"/>
                <input type="password" name="password"
                        class="form" placeholder="비밀번호"
                        v-model="user.password"/>
                <button type="submit" class="btn-signup">회원가입</button>
            </form>
            <span class="signup-link"><router-link to="/login">로그인하러 가기</router-link></span>
        </div>
    </div>
</template>

<script>
import notification from '@/libs/notification';

export default {
  name: "signup",
  data: () => ({
    user: {
      name: '',
      email: '',
      password: '',
    }
  }),
  methods: {
    async signup() {
      if (this.user.name === '') {
        notification.warn('이름을 입력해주세요.')
        return
      }
      if (this.user.email === '') {
        notification.warn('이메일을 입력해주세요.')
        return
      }
      if (this.user.password === '') {
        notification.warn('비밀번호를 입력해주세요.')
        return
      }
      this.axios.post('/auth/signup', this.user)
      .then(() => {
        notification.success('회원가입을 완료했습니다.', () => {
          this.$router.push('/login');
        });
      })
      .catch(err => {
        notification.error(err)
      })
    }
  }
}
</script>

<style scoped>
.signup-container {
  display: flex;
  justify-content: center;
}
.signup-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #fff;
  border-radius: 3px;
  box-shadow: 0 1px 2px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
  width: 500px;
  margin-top: 30px;
  padding: 55px 35px;
}
.signup-content form {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 13px;
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
.btn-signup {
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
.signup-title {
    font-size: 1.5em;
    font-weight: 500;
    margin-top: 0;
    margin-bottom: 45px;
    color: rgba(0, 0, 0, 0.65);
}
</style>