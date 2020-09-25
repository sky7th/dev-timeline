<template>
  <div class="post-like">
    <div class="like">
      <div class="btn-like">
        <font-awesome-icon v-if="isLike" :icon="['fas', 'heart']" @click="cancelLike()" class="btn-like-full"/>
        <font-awesome-icon v-else :icon="['far', 'heart']" @click="doLike()"/>
      </div>
      <span class="like-count">{{ likeCount }}</span>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import notification from '../../libs/notification';
import { faHeart as fasHeart } from '@fortawesome/free-solid-svg-icons' 
import { faHeart as farHeart } from '@fortawesome/free-regular-svg-icons' 
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core' 

faLibrary.add(fasHeart, farHeart) 

export default {
  props: ['isLike', 'postId', 'likeCount'],
  computed: {
    ...mapGetters(['selectedMenu', 'post', 'posts'])
  },
  methods: {
    doLike() {
      event.stopPropagation();
      this.axios.post(`${process.env.VUE_APP_API}/api/v1/posts/${this.postId}/likes/`)
      .then(() => {
        var post = this.posts.find(v => v.id === this.postId);
        post.like = true;
        this.post.like = true;
        post.likeCount += 1;
        this.post.likeCount += 1;
      }).catch(error => {
        notification.warn(error.response.data.message);
      })
    },
    cancelLike() {
      event.stopPropagation();
      this.axios.delete(`${process.env.VUE_APP_API}/api/v1/posts/${this.postId}/likes/`)
      .then(() => {
        var post = this.posts.find(v => v.id === this.postId);
        post.like = false;
        this.post.like = false;
        post.likeCount -= 1;
        this.post.likeCount -= 1;
      }).catch(error => {
        notification.warn(error.response.data.message);
      })
    }
  },
}
</script>

<style scoped>
.post-like {
  position: absolute;
  color: #7a7a7a;
  font-size: 14px;
}
.like-count {
  margin-left: 4px;
  font-size: 12px;
}
.btn-like {
  cursor: pointer;
  display: inline-block;
}
.like {
}
.btn-like:hover {
  font-size: 22px;
  bottom: 5px;
  transition: all .1s ease-in;
}
.btn-like-full {
  color: #ff5784
}

</style>