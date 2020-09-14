<template>
  <div class="btn-up-wrapper">
    <font-awesome-icon :icon="['far', 'hand-point-up']" @click="scrollUp" class="btn-up"/>
  </div>
</template>

<script>
import { faHandPointUp } from '@fortawesome/free-regular-svg-icons' 
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core' 

faLibrary.add(faHandPointUp) 

export default {
  methods: {
    scrollUp() {
      window.scrollTo(0,0)
    }
  }
}
</script>

<style scoped>
.btn-up-wrapper {
  height: 100px;
  animation: btn-up 2s ease-in-out;
  animation-iteration-count: 9999;
  margin-top: 80px;
}
.btn-up {
  font-size: 35px;
  color: #5a5a5a;
  cursor: pointer;
}
</style>