import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

//在vue全局引入ant-design-vue组件库和样式
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(Antd) //在vue全局引入ant-design-vue组件库和样式

app.mount('#app')
