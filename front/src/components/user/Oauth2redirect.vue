<template>
    <div>

    </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "oauth2redirect",
  created() {
    const token = this.$route.query.token;
    if (token) {
      this.$store.commit('setToken', token);
      this.$emit('getCurrentUsers');
      this.$router.replace('/')
    }
    else {
      this.setToken(null)
      this.$router.replace("/")
    }
  },
  methods: {
    ...mapActions(['setToken'])
  }
}
</script>

<style scoped>
</style>