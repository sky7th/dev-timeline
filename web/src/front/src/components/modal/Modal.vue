<template>
  <div>
    <div class="modal-background" @click="closePopupInDelay">
    </div>
    <div class="modal" :class="{ 'close-in-delay': isClickCloseBtn }">
      <div class="close" @click="closePopupInDelay">x</div>

      <div v-if="selectedMenu==='link-posts'" class="wrapper">
        <LinkPostViewModal v-if="isStateRead" @closePopup="closePopupInDelay"/>
        <LinkPostWriteModal v-else @closePopup="closePopupInDelay"/>
      </div>
      
    </div>
  </div>
</template>

<script>
import LinkPostWriteModal from '@/components/modal/LinkPostWriteModal'
import LinkPostViewModal from '@/components/modal/LinkPostViewModal'
import { mapGetters, mapActions } from "vuex";
import Constant from '@/constant/Constant'

export default {
  components: {
    LinkPostWriteModal,
    LinkPostViewModal
  },
  data() {
    return {
      isClickCloseBtn: false
    }
  },
  created() {
    document.getElementsByTagName('body')[0].style['overflow-y'] = 'hidden';
  },
  computed: {
    ...mapGetters(['selectedMenu', 'postState']),
    isStateRead() {
      return this.postState===Constant.READ;
    }
  },
  methods: {
    ...mapActions(['offModalState', 'updatePostState', 'updatePost']),
    closePopupInDelay() {
      this.isClickCloseBtn = true;
      document.getElementsByTagName('body')[0].style['overflow-y'] = 'scroll';
      setTimeout(() => {
        this.offModalState();
        this.updatePostState(Constant.READ);
        this.updatePost({});
      }, 500);
    }
  }
}
</script>

<style scoped>
.modal {
  position: fixed;
  display: flex;
  flex-direction: column;
  left: 50%;
  top: 50%;
  border-radius: 7px;
  transform: translate(-50%, -50%);
  max-height: 90%;
  width: 500px;
  max-width: 100%;
  opacity: 1;
  background-color: #fff;
  padding: 0px 4px 15px 10px;
  border-radius: 3px;
  box-shadow: 0 1px 2px 0 rgba(9,30,66,0.25), 0 0 1px 0 rgba(9,30,66,0.31);
  animation: superman 0.5s ease-in-out;
  z-index: 1000;
  transition: all 600ms cubic-bezier(0.36, 0, 0.07, 1);
}
.close-in-delay {
  animation: superman-reverse 0.5s ease-in-out;
}
.modal-background {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: rgba(120, 120, 120, 0.4);
  animation: fadeIn 0.3s ease-in-out;
  z-index: 1000;
}
.close {
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  color: #7a7a7a;
  text-align: end;
  padding: 5px;
}
.wrapper {
  overflow-y: scroll;
  width: 100%;
}
::-webkit-scrollbar { width: 3.5px; } /* 스크롤 바 */
::-webkit-scrollbar-track { background-color:#fff; } /* 스크롤 바 밑의 배경 */
::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
</style>