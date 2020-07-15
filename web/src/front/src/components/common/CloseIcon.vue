<template>
  <div v-if="isClose()" class="new-icon">new</div>
</template>

<script>
const NEW_PERIOD = 7;

export default {
  props: ['date'],
  methods: {
    isClose() {
      let date = new Date(this.date);
      let nowDate = new Date();
      let newPeriodDate = new Date();
      newPeriodDate.setDate(newPeriodDate.getDate() - NEW_PERIOD)
      
      return date.getTime() >= newPeriodDate.getTime() && date.getTime() <= nowDate.getTime();
    }
  }
}
</script>

<style scoped>
.new-icon {
  position: absolute;
  color: red;
  font-size: 11px;
  right: 6px;
  bottom: -1px;
  animation: twinkle 1s ease-in-out;
  animation-iteration-count: 9999;
  font-weight: bold;
}
</style>