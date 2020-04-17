<template>
  <div class="link-post-modal">
    <div class="wrapper">
      <div class="description">title</div>
      <InputForm class="title-input" name="title" placeholder="제목" v-model="linkPost.title" />
    </div>
    <div class="wrapper">
      <div class="description">link</div>
      <InputForm class="link-input" name="linkUrl" type="text" placeholder="https://www.devtimeline.com/" v-model="linkPost.linkUrl"/>
    </div>
    
    <div class="wrapper content">
      <div class="description">description</div>
      <TextareaForm class="content-text" name="content" placeholder="설명을 적어주세요." v-model="linkPost.content"/>
    </div>
    <div class="bottom-wrapper">
      <BlueButton class="btn-submit" type="submit" :name="'작성'"/>
    </div>
  </div>
</template>

<script>
import InputForm from '@/components/common/InputForm'
import TextareaForm from '@/components/common/TextareaForm'
import BlueButton from '@/components/common/button/BlueButton'

export default {
  data: () => ({
    linkPost: { 
      title: '',
      linkUrl: '',
      content: ''
    }
  }),
  components: {
    InputForm,
    TextareaForm,
    BlueButton
  }
}
</script>

<style scoped>
.link-post-modal {
  display: flex;
  flex-direction: column;
  height: 100%;
}
.wrapper {

}
.description {
  margin-bottom: 3px;
  font-size: 15px;
  color: #6a6a6a;
  margin-left: 5px;
}
.title-input {
  height: 34px;
}
.link-input {
  height: 34px;
}
.content-text {
  height: 100%;
}
.content {
  flex: 1;
  display: flex;
  flex-direction: column;
}
.btn-submit {
  width: 100px;
}
.bottom-wrapper {
  text-align: right;
  margin-top: 10px;
}
::-webkit-scrollbar { width: 3.2px; } /* 스크롤 바 */
::-webkit-scrollbar-track { background-color:#f7f7f7; } /* 스크롤 바 밑의 배경 */
::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
</style>