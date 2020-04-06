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
import { mapActions } from "vuex";

export default {
  data() {
    return {
      checkedCompanies: []
    }
  },
  watch: {
    checkedCompanies() {
      this.updateCheckedCompanies({ checkedCompanies: this.checkedCompanies })
      this.resetOffset()
      this.updatePosts()
    }
  },
  methods: {
    ...mapActions(['updateCheckedCompanies', 'updatePosts', 'resetOffset'])
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