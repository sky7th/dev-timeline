<template>
  <button class="btn" @click="handlerClick">
    {{ name }}
  </button>
</template>

<script>

export default {
  props: ['value', 'name'],
  methods: {
    handlerClick() {
      this.$emit('click');
    }
  }
}
</script>

<style scoped>
.btn {
  cursor: pointer;
  padding: 6px 8px;
  background-color: #fff;
  border: 1px solid #2d92f1;
  border-radius: 5px;
  font-size: 13px;
  font-weight: 500;
  color: #2d92f1;
}
.btn:hover {
  background-color: #2d92f1;
  color: #fff;
}
</style>
