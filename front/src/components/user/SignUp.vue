<template>
    <div class="signup-container">
      <notifications group="notify" position="bottom center"/>
        <div class="signup-content">
            <h1 class="signup-title">dev-time</h1>
            <form @submit.prevent="signup">
                <input type="text" name="name"
                        class="form" placeholder="이름"
                        v-model="user.name"/>
                <input type="email" name="email"
                        class="form" placeholder="이메일"
                        v-model="user.email" ref="emailInput"/>
                <div class="default-message">인증을 위한 이메일이 발송됩니다.</div>
                <div class="check-message" ref="emailMessage"></div>
                <input type="password" name="password"
                        class="form" placeholder="비밀번호"
                        v-model="user.password" ref="passwordInput"/>
                <div class="default-message">비밀번호는 문자, 숫자, 특수 문자 하나씩 포함, 최소 6자리</div>
                <div class="check-message password-check-message" ref="passwordMessage"></div>
                <button type="submit" class="btn-signup">회원가입</button>
            </form>
            <span class="signup-link" @click="updateModalContent('LOGIN')">로그인하러 가기</span>
        </div>
    </div>
</template>

<script>
import notification from '@/libs/notification';
import { mapActions } from "vuex";

export default {
  name: "signup",
  data: () => ({
    user: {
      name: '',
      email: '',
      password: '',
    }
  }),

  mounted() {
    this.emailInfo = {
      state: false,
      inputElement: this.$refs.emailInput,
      messageElement: this.$refs.emailMessage
    }
    const regEmail = /([\w-.]+)@((\[[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    this.checkEmailForm(this.emailInfo, regEmail, state => this.loadCheckMessage(this.emailInfo, state, '올바른 형식의 이메일 입니다.', '올바른 형식의 이메일이 아닙니다.'));

    this.passwordInfo = {
      state: false,
      inputElement: this.$refs.passwordInput,
      messageElement: this.$refs.passwordMessage
    }
    const regPassword = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,}$/;
    this.checkEmailForm(this.passwordInfo, regPassword, state => this.loadCheckMessage(this.passwordInfo, state, '사용할 수 있는 비밀번호 입니다.', '사용할 수 없는 비밀번호 입니다.'));
  },

  methods: {
    ...mapActions(['updateModalContent', 'updateIsLoadingContent']),
    async signup() {
      if (this.user.name === '') {
        notification.warn('이름을 입력해주세요.')
        return
      }
      if (this.user.email === '') {
        notification.warn('이메일을 입력해주세요.')
        return
      }
      if (!this.emailInfo.state) {
        notification.warn('올바른 형식의 이메일이 아닙니다.')
        return
      }
      if (this.user.password === '') {
        notification.warn('비밀번호를 입력해주세요.')
        return
      }
      if (!this.passwordInfo.state) {
        notification.warn('사용할 수 없는 비밀번호 입니다.')
        return
      }
      this.updateIsLoadingContent(true);

      this.axios.post('/auth/signup', this.user)
      .then(() => {
        notification.success('회원가입을 완료했습니다.', () => {
          alert('인증 이메일이 발송되었습니다.');
          this.updateModalContent('LOGIN')
        });
      })
      .catch(err => {
        notification.error(err)
      }).finally(() => this.updateIsLoadingContent(false))
    },

    checkEmailForm(info, reg, loadCheckMessage) {
      info.inputElement.addEventListener('keyup', () => {
        var value = info.inputElement.value;
        let state = reg.test(value);
        loadCheckMessage(state);
      })
    },
    
    loadCheckMessage(info, state, messageForTrue, messageForFalse) {
      if (state === true) {
        info.state = true;
        info.messageElement.innerHTML = messageForTrue;
        info.messageElement.classList.remove('hide');
        info.messageElement.style.color = 'green';
      } else {
        info.state = false;
        info.messageElement.innerHTML = messageForFalse;
        info.messageElement.classList.remove('hide');
        info.messageElement.style.color = 'red';
      }
    }
  }
}
</script>

<style scoped>
.signup-container {
  display: flex;
  justify-content: center;
  animation: fadeIn 0.3s ease-in-out;
}
.signup-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #fff;
  border-radius: 3px;
  width: 500px;
  padding: 25px 35px 37px 35px;
}
.signup-content form {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 13px;
}
.form {
  display: block;
  height: 38px;
  width: 300px;
  border: 1px solid #bababa;
  border-radius: 3px;
  box-shadow: 0px 0px 1px 0px rgba(0, 0, 0, 0.2) inset;
  margin-bottom: 12px;
  padding: 0 11px;
  font-size: 14px;
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
  font-size: 28px;
  font-weight: bold;
  color: #4E5763;
  margin-top: 0;
  margin-bottom: 30px;
}
input::placeholder {
  font-size: 14px;
}
.check-message {
  font-size: 12px;
  margin-top: -6px;
  margin-bottom: 16px;
}
.default-message {
  font-size: 12px;
  color: #888;
  margin-bottom: 17px;
  margin-top: -6px;
}
.password-check-message {
  margin-bottom: 20px;
}
</style>