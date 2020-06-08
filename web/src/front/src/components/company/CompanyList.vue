<template>
  <div class="company-list">
    <ul>
      <li
        v-for="({
          companyName, 
          companyType,
          logoUrl}) in companyInfos"
        :key="companyType"
        :class="{ check: checkedCompanies.indexOf(companyType) != -1 }"
      >
        <input type="checkbox" :id="companyType" :value="companyType" 
          v-model="checkedCompanies"
        >
        <label :style="{ background: `url(${logoUrl}) left/38px no-repeat`, 'background-position': 'center' }" :for="companyType">{{ companyName }}</label>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import notification from '@/libs/notification';

export default {
  data() {
    return {
      companyInfos: [],
      checkedCompanies: []
    }
  },
  created() {
      this.findCompanyByUrlType();
  },
  watch: {
    checkedCompanies() {
      this.updateCheckedCompanies({ checkedCompanies: this.checkedCompanies });
      this.resetOffset();
      this.updatePosts();
    },
    selectedMenu() {
        this.findCompanyByUrlType();
    }
  },
  computed: {
      ...mapGetters(['selectedMenu'])
  },
  methods: {
    ...mapActions(['updateCheckedCompanies', 'updatePosts', 'resetOffset']),
    findCompanyByUrlType() {
        let companyUrlType = this.getCompanyUrlTypeFromSelectedMenu(this.selectedMenu);
        this.axios.get(`${process.env.VUE_APP_API}/api/v1/company?companyUrlType=${companyUrlType}`)
        .then(response => {
            this.companyInfos = response.data.data;
            
        }).catch(() => {
            notification.warn('기업 리스트를 불러오지 못했습니다.');
        });
    },
    getCompanyUrlTypeFromSelectedMenu(selectedMenu) {
        return selectedMenu.split('-')[0].toUpperCase();
    }
  }
}
</script>

<style scoped>
.company-list {
  margin: 0px 30px 13px 30px;
  padding: 14px 0 0px 0;
  border-bottom: 2px solid #dadada;
  animation: fadeIn 0.3s ease-in-out;
}
.company-list ul {
  height: 85px;
  display: flex;
  overflow-x: scroll;
  white-space: nowrap;
}
.company-list li {
  padding: 8px 7px 5px 7px;
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
    padding-top: 64px;
    margin-top: -20px;
    background-size: 38px;
    color: #4E5763;
    font-weight: bold;
    background-position: inherit;
    font-size: 14px;
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