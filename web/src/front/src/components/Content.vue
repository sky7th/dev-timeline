<template>
  <div class="content">
    <!-- <Recruit/> -->
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
    <div class="post-list">
      <ul>
        <li
          v-for="({
            id, 
            companyTypeName, 
            companyUrlTypeName, 
            title,
            closingDate }) in posts"
          :key="id"
        >
          <div>{{ companyTypeName }}</div>
          <div>{{ companyUrlTypeName }}</div>
          <div>{{ title }}</div>
          <div>{{ closingDate }}</div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
// import Recruit from '@/components/content/Recruit';

export default {
  // components: {
  //   Recruit
  // }
  props: {
    posts: { type: Array, default: () => [] }
  },
  data() {
    return {
      checkedCompanies: []
    }
  },
  watch: {
    checkedCompanies() {
      this.$emit('updateCompanies', this.checkedCompanies)
      this.$emit('updatePosts')
    }
  }
}
</script>

<style scoped>
.content {
  height: 100%;
  width: 100%;
  position: absolute;
  margin-left: 100px;
  margin-top: 50px;
  animation: fadeIn 0.3s ease-in-out;
  z-index: 900;
}
.post-list {
  padding: 20px 20px;
}
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