<template>
  <div>
    <router-view @getUserDetails="getUserDetails"/>
  </div>
</template>

<script>
import "@/assets/css/styles.css";
import notification from './libs/notification';
import { mapActions, mapGetters } from "vuex";

export default {
  created(){
    this.getUserDetails();
  },
  computed: {
    ...mapGetters(['currentUser'])
  },
  methods: {
    ...mapActions(['setUserDetail']),
    async getUserDetails() {
      try {
        const token = this.$store.getters.token;
        if (token===null || token==='null' || token ==='') {
          if (this.currentUser !== null) {
            notification.success("토큰이 만료되었습니다. 다시 로그인해주세요", () => {
              this.logout();
            });
          }
          return;
        }

        const response = await this.axios.get(`/user/me`);
        if (response.status === 200) {
          this.setUserDetail(response.data)
        }
      } catch (err) {
        notification.error(err, "사용자 정보를 불러올 수 없습니다.", () => {
          this.logout();
        });
      }
    }
  }
};
</script>

<style scoped>

</style>