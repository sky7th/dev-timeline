<template>
  <div class="modal-background">
    <div class="modal">
      <div class="close" @click="offModalState">x</div>
      <LinkPostModal v-if="selectedMenu==='link-posts'"/>
    </div>
  </div>
</template>

<script>
import LinkPostModal from '@/components/modal/LinkPostModal'
import { mapGetters, mapActions } from "vuex";

export default {
  components: {
    LinkPostModal
  },
  computed: {
    ...mapGetters(['selectedMenu'])
  },
  methods: {
    ...mapActions(['offModalState'])
  }
}
</script>

<style scoped>
.modal {
  position: absolute;
  left: 50%;
  top: 50%;
  border-radius: 7px;
  transform: translate(-50%, -50%);
  height: 500px;
  width: 400px;
  opacity: 1;
  background-color: #fff;
  padding: 30px 10px 10px 10px;
  border-radius: 3px;
  box-shadow: 0 1px 2px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
  animation: superman 0.5s ease-in-out;
}
.modal-background {
  position: fixed;
  top: 23px;
  left: 40px;
  height: 100%;
  width: 100%;
  background-color: rgba(150, 150, 150, 0.2);
  animation: fadeIn 0.3s ease-in-out;
}
.close {
  position: absolute;
  right: 12px;
  top: 6px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
}
::-webkit-scrollbar { width: 3.2px; } /* 스크롤 바 */
::-webkit-scrollbar-track { background-color:#f7f7f7; } /* 스크롤 바 밑의 배경 */
::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
</style>