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
  created() {
    console.log(Constant)
  },
  data() {
    return {
      isClickCloseBtn: false
    }
  },
  computed: {
    ...mapGetters(['selectedMenu', 'postState']),
    isStateRead() {
      return this.postState===Constant.READ;
    }
  },
  methods: {
    ...mapActions(['offModalState', 'updatePostState']),
    closePopupInDelay() {
      this.isClickCloseBtn = true;
      setTimeout(() => {
        this.offModalState();
        this.updatePostState(Constant.READ);
      }, 500);
    }
  }
}
</script>

<style scoped>
.modal {
  position: fixed;
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
  z-index: 1000;
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
  position: absolute;
  right: 12px;
  top: 6px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  color: #7a7a7a;
}
.wrapper {
  height: 100%;
}
</style>