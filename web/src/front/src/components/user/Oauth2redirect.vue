<template>
    <div>

    </div>
</template>

<script>
export default {
  name: "oauth2redirect",
  created() {
    const token = this.$route.query.token;
    if (token) {
      this.$store.commit('setToken', token);
      this.$emit('getUserDetails');
      this.$router.replace('/')
    }
    else {
      this.$router.replace("/")
    }
  }
}
</script>

<style scoped>
</style>