<template>
  <div class="company-list">
    <ul>
      <li
        v-for="({
          name, 
          type,
          logoUrl}) in companyInfos"
        :key="type"
        :class="{ check: checkedCompanies.indexOf(type) != -1 }"
      >
        <input type="checkbox" :id="type" :value="type" 
          v-model="checkedCompanies"
        >
        <label class="company-logo" :style="{ 'background-image': `url(${logoUrl})`}" :for="type">{{ name }}</label>
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
      document.querySelector('.content-container').scrollTo(0,0)
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
        this.axios.get(`${process.env.VUE_APP_API}/api/v1/companies?companyUrlType=${companyUrlType}`)
        .then(response => {
            this.companyInfos = response.data.companies;
            
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
  margin: 0px 30px 10px 30px;
  padding: 22px 0 0px 0;
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
}
.company-list li + li {
  margin-left: 13px;
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
    min-width: 38px;
    text-align: center;
    padding-top: 64px;
    margin-top: -20px;
    background-size: 38px;
    color: #4E5763;
    font-weight: bold;
    background-position: center;
    background-repeat: no-repeat;
    font-size: 14px;
}
.check {
  background-color: #dadada;
}
@media screen and (max-width: 480px) {
  .company-list {
    margin: 0px 10px 10px 10px;
  }
  .company-list ul {
    height: 75px;
  }
  .company-list li + li {
    margin-left: 5px;
  }
  input[type=checkbox] + label { 
    background-size: 32px;
    font-size: 12px;
    padding-top: 56px;
  }
}
</style>