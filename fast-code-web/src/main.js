import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import axios from 'axios'
axios.defaults.baseURL="/fastCode"
Vue.prototype.$axios = axios;

Vue.use(Antd);
new Vue({
  render: h => h(App),
}).$mount('#app')
