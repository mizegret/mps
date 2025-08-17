import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import '@unocss/reset/tailwind-compat.css'
import 'uno.css'

const app = createApp(App)

app.use(router)

app.config.errorHandler = (err, _, info) => {
  console.error('[Vue Error]', err, info)
}

router.isReady().then(() => {
  app.mount('#app')
})
