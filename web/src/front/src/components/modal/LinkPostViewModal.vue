<template>
  <div class="link-post-modal">
    <div class="wrapper">
      <div class="description">title</div>
      <div class="title-input">{{ linkPost.title }}</div>
    </div>
    <div class="wrapper">
      <div class="description">link</div>
      <div class="link-input">{{ linkPost.link }}</div>
    </div>
    <div class="wrapper">
      <div class="description">tags <span style="font-size: 12px; margin-left: 4px;">(태그 입력 후 엔터를 꾸욱)</span></div>
      <ul class="tag-wrapper">
        <li v-for="(tag, i) in linkPost.tags" :key="i" class="tag-add">
          <span class="tag-name">{{ tag.name }}</span>
        </li>
      </ul>
    </div>
    <div class="wrapper content">
      <div class="description">description</div>
      <div class="content-input">{{ linkPost.content }}</div>
    </div>
    <div v-if="isPossibleUpdate" class="bottom-wrapper">
      <BlueButton class="btn-submit" type="submit" :name="'수정'"
        @click="update"/>
    </div>
    <notifications group="notify" position="bottom center"/>
  </div>
</template>

<script>
import BlueButton from '@/components/common/button/BlueButton'
import { mapGetters, mapActions } from "vuex";
import notification from '../../libs/notification';
import Constant from '@/constant/Constant';

export default {
  data: () => ({
    linkPost: { 
      title: '',
      linkUrl: '',
      content: '',
      tags: [],
      user: {}
    },
    isPossibleUpdate: false
  }),
  created() {
    var postId = localStorage.getItem('postId');
    this.axios.get(`${process.env.VUE_APP_API}/api/v1/link-posts/${postId}`)
      .then(response => {
        this.linkPost = response.data.data;
        
        if (this.linkPost.user.id == this.currentUser.id)
          this.isPossibleUpdate = true
      }).catch(() => {
        notification.warn('글을 불러오지 못했습니다.');
      })
  },
  components: {
    BlueButton
  },  
  computed: {
    ...mapGetters(['currentUser'])
  },
  methods: {
    ...mapActions(['offModalState', 'resetOffset', 'updatePosts', 'updatePostState']),
    update() {
      localStorage.setItem('post', JSON.stringify(this.linkPost));
      this.updatePostState(Constant.UPDATE);
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