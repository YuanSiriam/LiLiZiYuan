import { createApp } from 'vue'
import App from './App.vue'
import './assets/tailwind.css'
import router from './router'
import axios from "axios";
import alertComponent from "@/components/AlertComponent.vue";


const app = createApp(App)
app.use(router).mount('#app')
app.component('alertComponent',alertComponent)



axios.interceptors.request.use(config => {
    const jwt = localStorage.getItem('jwt')

    if (jwt) {
        config.headers.Authorization = `Bearer ${jwt}`
    }

    return config
}, error => {
    return Promise.reject(error)
})

