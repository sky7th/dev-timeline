<template>
  <div>
    <router-view @getUserDetails="getUserDetails"/>
  </div>
</template>

<script>
import "@/assets/css/styles.css";
import notification from './libs/notification';

export default {
  created(){
    this.getUserDetails();
  },
  methods: {
    async getUserDetails() {
      try {
        const token = this.$store.getters.token;
        if (token==null || token==='null' || token ==='')
          return;

        const response = await this.axios.get(`/user/me`);
        if (response.status === 200) {
          this.$store.commit('setUserDetail', response.data);
        }
      } catch (err) {
        notification.error(err, "사용자 정보를 불러올 수 없습니다.",() => {
          this.logout();
        });
      }
    }
  }
};
</script>

<style scoped>

</style>