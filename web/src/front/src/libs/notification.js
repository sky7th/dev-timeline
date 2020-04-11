import vue from '../main';

export default {
  success(response, msg, block) {
    vue.$notify({
      group: 'notify',
      type: 'success',
      duration: 3000,
      text: msg,
    });
    block();
  },

  error(error, block) {
    vue.$notify({
      group: 'notify',
      type: 'error',
      duration: 3000,
      title: error.response.state,
      text: error.response.data.message,
    });
    block();
  },

  notify(msg) {
    vue.$notify({
      group: 'notify',
      type: 'warning',
      text: msg,
    });
  },

  warn(msg) {
    vue.$notify({
      group: 'notify',
      type: 'error',
      text: msg,
    });
  },
};