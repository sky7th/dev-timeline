<template>
  <div class="link-post-modal">
    <div class="wrapper">
      <div class="description">title</div>
      <InputForm class="title-input" name="title" placeholder="제목" 
        v-model="linkPost.title" 
        @keypressEnter="moveFocusToLink"/>
    </div>
    <div class="wrapper">
      <div class="description">link</div>
      <InputForm class="link-input" name="linkUrl" type="text" placeholder="https://www.devtimeline.com/" 
        v-model="linkPost.linkUrl"
        @keypressEnter="moveFocusToTag"/>
    </div>
    <div class="wrapper">
      <div class="description">tags</div>
      <ul class="tag-wrapper" @click="moveFocusToTag">
        <li v-for="(tag, i) in linkPost.tags" :key="i" class="tag-add">
          <span class="tag-name">{{ tag.name }}</span>
          <span class="tag-close" @click="removeTag($event, tag.forId)">x</span>
        </li>
        <li class="tag-input">
          <input type="text" v-model="tagName" 
            @keypress.enter="addTag"
            @blur="addTag"
            @keydown="getLengthByLanguage">
        </li>
      </ul>
    </div>
    <div class="wrapper content">
      <div class="description">description</div>
      <TextareaForm class="content-input" name="content" placeholder="설명을 적어주세요." v-model="linkPost.content"/>
    </div>
    <div class="bottom-wrapper">
      <BlueButton class="btn-submit" type="submit" :name="'작성'"
        @click="submit"/>
    </div>
  </div>
</template>

<script>
import InputForm from '@/components/common/InputForm'
import TextareaForm from '@/components/common/TextareaForm'
import BlueButton from '@/components/common/button/BlueButton'
import { mapActions } from "vuex";
import notification from '../../libs/notification';

export default {
  data: () => ({
    linkPost: { 
      title: '',
      linkUrl: '',
      content: '',
      tags: []
    },
    tagName: '',
  }),
  components: {
    InputForm,
    TextareaForm,
    BlueButton
  },  
  methods: {
    ...mapActions(['offModalState', 'resetOffset', 'updatePosts']),
    submit() {
      if (this.linkPost.title == '') {
        notification.warn('제목을 입력해주세요.');
        document.querySelector('.title-input').focus();
        return;
      }
      else if (this.linkPost.linkUrl == '') {
        notification.warn('공유하실 url 링크를 입력해주세요.');
        document.querySelector('.link-input').focus();
        return;
      }
      this.axios.post(`${process.env.VUE_APP_API}/api/v1/link-posts`, this.linkPost)
        .then(() => {
          notification.success('좋은 링크 공유해주셔서 감사합니다 ^-^');
          this.handlerRefresh();
        }).catch(() => {
          notification.warn('글쓰기에 실패했습니다.');
        })
    },
    handlerRefresh() {
      this.offModalState();
      this.resetOffset();
      this.updatePosts();
    },
    addTag() {
      this.linkPost.tags.push({ id: null, forId: new Date().getTime(), name: this.tagName });
      this.tagName = '';
    },
    removeTag($event, forId) {
      this.linkPost.tags = this.linkPost.tags.filter(tag => tag.forId !== forId);
    },
    getLengthByLanguage($event) {
      var kor_cnt = 0;
      var another_cnt = 0;
      var check_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
      var tagName = this.tagName;

      for (var i = 0; i < tagName.length; i++) {
        if (check_kor.test(tagName[tagName.length - 1]))
          kor_cnt += 1;
        else
          another_cnt += 1;
      }
      $event.target.style.width = 18 + another_cnt * 7 + kor_cnt * 13 + 'px';
    },
    moveFocusToLink() {
      document.querySelector('.link-input').focus();
    },
    moveFocusToTag() {
      document.querySelector('.tag-input > input').focus();
    },
    moveFocusToContent() {
      document.querySelector('.content-input').focus();
    }
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
  margin-bottom: 13px;
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
.content-input {
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
}
.tag-wrapper {
  border: 1.5px solid #dadada;
  border-radius: 3px;
  box-shadow: 0px 0px 2px 0px rgba(0, 0, 0, 0.2) inset;
  cursor: text;
  padding: 5px 4px;
}
.tag-wrapper .tag-add {
  font-size: 14px;
  line-height: 1.2;
  display: inline-flex;
  align-items: center;
  margin: 2px;
  padding: 2px;
  border: 0;
  border-radius: 4px;
  color: #fff;
  overflow: hidden;
  background-color: #2d92f1;
}
.tag-input {
  font-size: 14px;
  line-height: 1.2;
  display: inline-flex;
  height: 30px;
}
.tag-input input {
  border: none;
  outline: none;
  margin: 0 8px;
  color: #6a6a6a;
}
.tag-name {
  line-height: 1.1;
  font-weight: 700;
  padding: 5px 7px;
  font-size: 13px;
  margin-bottom: 1px;
}
.tag-close {
  border: 0;
  margin: 0;
  padding: 5px;
  cursor: pointer;
}
.tag-close:hover {
  padding-right: 4px;
  font-weight: bold;
}
::-webkit-scrollbar { width: 3.2px; } /* 스크롤 바 */
::-webkit-scrollbar-track { background-color:#f7f7f7; } /* 스크롤 바 밑의 배경 */
::-webkit-scrollbar-thumb { background: #dadada; } /* 실질적 스크롤 바 */
::-webkit-scrollbar-thumb:hover { background: #9a9a9a; } /* 실질적 스크롤 바 위에 마우스를 올려다 둘 때 */
::-webkit-scrollbar-thumb:active { background: #6a6a6a; } /* 실질적 스크롤 바를 클릭할 때 */
::-webkit-scrollbar-button { display: none; } /* 스크롤 바 상 하단 버튼 */
</style>