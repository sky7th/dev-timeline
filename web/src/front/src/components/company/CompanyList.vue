<template>
  <div class="company-list">
    <ul>        
      <li :class="{ check: checkedCompanies.indexOf('NAVER') != -1 }">
        <input type="checkbox" id="naver" value="NAVER" 
          v-model="checkedCompanies"
        >
        <label class="naver" for="naver">네이버</label>
      </li>
      <li :class="{ check: checkedCompanies.indexOf('KAKAO') != -1 }">
        <input type="checkbox" id="kakao" value="KAKAO" 
          v-model="checkedCompanies"
        >
        <label class="kakao" for="kakao">카카오</label>
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
  margin: 0px 30px 13px 30px;
  padding: 13px 0 0px 0;
  border-bottom: 2px solid #dadada;
}
.company-list ul {
  display: flex;
  overflow-x: scroll;
  white-space: nowrap;
}
.company-list li {
  padding: 8px 15px 5px 15px;
  display: inline-block;
  margin-right: 13px;
}
::-webkit-scrollbar { width: 3.2px; } /* 스크롤 바 */
::-webkit-scrollbar-track { background-color:#f7f7f7; } /* 스크롤 바 밑의 배경 */
::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
input[type=checkbox] { display:none; }

input[type=checkbox] + label { 
  display: inline-block;
  cursor: pointer;
  padding-top: 51px;
  background-size: 44px;
  color: #4E5763;
  font-weight: bold;
  background-position: inherit;
}
.naver {
  background: url('https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQS1ZRzTCwb49Z6sdxtxLM0jJ1SoMDtlBQ_DBHlghI2o6e_9Mov&usqp=CAU') left/22px no-repeat; 
}
.kakao {
  background: url('https://i.pinimg.com/564x/86/39/85/8639859040131440e24166acfe00dcb0.jpg') left/22px no-repeat; 
}
.check {
  background-color: #dadada;
}
</style>