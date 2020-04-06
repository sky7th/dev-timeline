<template>
  <div class="company-list">
    <ul>        
      <li>
        <input type="checkbox" id="naver" value="NAVER" 
          v-model="checkedCompanies"
        >
        <label class="naver" for="naver">Naver</label>
      </li>
      <li>
        <input type="checkbox" id="kakao" value="KAKAO" 
          v-model="checkedCompanies"
        >
        <label class="kakao" for="kakao">Kakao</label>
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
  padding: 3px 20px 0px 20px;
  border-bottom: 1px solid black;
}
.company-list ul {
  display: flex;
}
.company-list li {
  margin: 10px 15px;
}
input[type=checkbox] { display:none; }

input[type=checkbox] + label { 
  display: inline-block;
  cursor: pointer;
  padding-top: 46px;
  background-size: 41px;
  background-position: inherit;
}
input[type=checkbox]:checked + label {  }
.naver {
  background: url('https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQS1ZRzTCwb49Z6sdxtxLM0jJ1SoMDtlBQ_DBHlghI2o6e_9Mov&usqp=CAU') left/22px no-repeat; 
}
.kakao {
  background: url('https://i.pinimg.com/564x/86/39/85/8639859040131440e24166acfe00dcb0.jpg') left/22px no-repeat; 
}
</style>