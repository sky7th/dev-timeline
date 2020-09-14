import Vue from 'vue'
import App from './App.vue'
import router from "./router";
import store from "./store";
import notifications from 'vue-notification';
import axios from './libs/axios.custom'
import constant from './constant/Constant'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas } from '@fortawesome/free-solid-svg-icons' 
import { far } from '@fortawesome/free-regular-svg-icons' 
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core'

faLibrary.add(fas, far) 

Vue.use(constant);
Vue.use(notifications);
Vue.prototype.axios = axios;
Vue.config.productionTip = false

Vue.component('font-awesome-icon', FontAwesomeIcon)

const app = new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app')

export default app;