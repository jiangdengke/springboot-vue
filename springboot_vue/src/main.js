import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//引入global.css
import '@/assets/css/global.css'
//ElementPlus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//引入ElentmentPlus中文包
import zhCn from 'element-plus/es/locale/lang/zh-cn'
createApp(App).use(store).use(router).use(ElementPlus,{locale:zhCn}).mount('#app')
