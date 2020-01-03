import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

Vue.config.productionTip = false

import ElementUi from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'pagoda-ui/src/theme/theme1.scss'

Vue.use(ElementUi, { size: 'mini' })

import pagodaComponents from 'pagoda-ui'

Vue.use(pagodaComponents)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
