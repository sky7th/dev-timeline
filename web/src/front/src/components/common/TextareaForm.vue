<template>
  <textarea type="text" class="textarea-form" :value="value" @input="updateText">
  </textarea>
</template>

<script>

export default {
  props: ['value'],
  methods: {
    updateText($event) {
      this.$emit('input', $event.target.value)
    }
  }
}
</script>

<style scoped>
.textarea-form {
  border: 1.5px solid #dadada;
  border-radius: 4px;
  box-shadow: 0px 0px 1px 0px rgba(0, 0, 0, 0.2) inset;
  padding: 7px 8px;
  word-break: break-all;
  resize: none;
  font-size: 14px;
  width: 100%;
  font-size: 15px;
  color: #6a6a6a;
}
.textarea-form::placeholder {
  color: #c2c2c2;
}
</style>