<template>
  <span v-if="isNew()" class="new-icon">new</span>
</template>

<script>
export default {
  props: ['date', 'period'],
  methods: {
    isNew() {
      let date = new Date(this.date);
      let nowDate = new Date();
      let newPeriodDate = new Date();
      newPeriodDate.setDate(newPeriodDate.getDate() - this.period)
      
      return date.getTime() >= newPeriodDate.getTime() && date.getTime() <= nowDate.getTime();
    }
  }
}
</script>

<style scoped>
.new-icon {
  position: absolute;
  color: red;
  font-size: 10px;
  left: 6px;
  top: -4px;
  animation: twinkle 1s ease-in-out;
  animation-iteration-count: 9999;
  font-weight: bold;
}
</style>