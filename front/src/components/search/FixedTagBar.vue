<template>
  <div class="fixed-tag-bar">
    <ul>
      <li 
        v-for="(tag, id) in fixedTags" 
        :key="id"
        @click="handlerAddTag(tag)"
      >
        <div class="tag">
          <span>#{{ tag }}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  data() {
    return {
      searchName: '',
      fixedTags: ['java', 'spring', 'aws', 'python', 'javascript', 'git', 'devops', 'msa', '클린코드', 'DB', '클라우드', 'django', 'flask', 'nodejs', 'typescript', 'scala', 'R', 'C++', '알고리즘']
    }
  },
  computed: {
    ...mapGetters(['tags']),
  },
  methods: {
    ...mapActions(['insertTag', 'resetOffset', 'updatePosts']),
    handlerAddTag(tag) {
      this.insertTag({ tagName: tag })
      this.resetOffset()
      this.updatePosts()
    }
  }
}
</script>

<style scoped>
.fixed-tag-bar > ul {
  font-weight: 300;
  line-height: 1.8rem;
  padding: 20px 10px 0px 10px;
  text-align: center;
  overflow-x: scroll;
  height: 85px;
  display: flex;
  margin-bottom: 15px;
}
.fixed-tag-bar > ul > li {
  display: inline-block;
  margin: 0.20rem 0.25rem;
  font-size: 0.9rem;
  padding: 0 10px;
  border: 1px solid #d6d6d6;
  border-radius: 5px;
  background-color: #ffffff;
  font-size: 0.75rem;
  height: 34px;
  white-space: nowrap;
}
.fixed-tag-bar > ul > li:hover {
  color: #2d92f1;
  background-color: #eeeeee;
  cursor: pointer;
  transition: background-color .2s;
}
</style>