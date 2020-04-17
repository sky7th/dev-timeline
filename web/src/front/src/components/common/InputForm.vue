<template>
  <input class="input-form" type="text" :value="value" @input="updateInput">
</template>

<script>

export default {
  props: ['value'],
  methods: {
    updateInput($event) {
      this.$emit('input', $event.target.value)
    }
  }
}
</script>

<style scoped>
.input-form {
  display: block;
  height: 41px;
  width: 300px;
  border: 1px solid #bababa;
  border-radius: 3px;
  box-shadow: 0px 0px 2px 0px rgba(0, 0, 0, 0.2) inset;
  margin-bottom: 10px;
  padding: 0 11px;
  width: 100%;
  font-size: 15px;
  color: #6a6a6a;
}
.input-form::placeholder {
  color: #c2c2c2;
}
</style>