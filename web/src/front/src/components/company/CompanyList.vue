<template>
  <div class="company-list">
    <ul>        
      <li>
        <input type="checkbox" id="naver" value="NAVER" 
          v-model="checkedCompanies"
        >
        <label for="Naver">Naver</label>
      </li>
      <li>
        <input type="checkbox" id="kakao" value="KAKAO" 
          v-model="checkedCompanies"
        >
        <label for="kakao">Kakao</label>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      checkedCompanies: []
    }
  },
  computed: {
    ...mapGetters(["posts"])
  },
  watch: {
    checkedCompanies() {
      this.updateCheckedCompanies({ checkedCompanies: this.checkedCompanies })
      this.updatePosts()
      // this.$emit('updateCompanies', this.checkedCompanies)
      // this.$emit('updatePosts')
    }
  },
  methods: {
    ...mapActions(['updateCheckedCompanies', 'updatePosts'])
  }
}
</script>

<style scoped>
.company-list {
  border-bottom: 1px solid black;
}
.company-list ul {
  display: flex;
}
.company-list li {
  margin: 15px 15px;
}
</style>