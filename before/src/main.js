import { createApp } from 'vue'
import App from './App.vue'
import './assets/tailwind.css'
import router from './router'
import axios from "axios";
import alertComponent from "@/components/AlertComponent.vue";
import Scrolling from "@/components/duplex/Scrolling.vue";


const app = createApp(App)
app.use(router).mount('#app')
app.component('alertComponent',alertComponent)
app.component('Scrolling',Scrolling)


axios.interceptors.request.use(config => {
    const jwt = localStorage.getItem('jwt')
    console.log("获取的jwt：",jwt)
    if (jwt) {
        config.headers.Authorization = `Bearer ${jwt}`
    }

    return config
}, error => {
    return Promise.reject(error)
})

axios.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 对响应错误做点什么
    console.error('后端错误:', error);
    // 可以在这里处理错误，例如显示错误信息给用户
    return Promise.reject(error);
});

// 解析jwt
export function getUserIdFromJwt(jwt) {
    console.log(jwt)
    if (jwt) {
        const payload = jwt.split('.')[1]
        const decodedPayload = JSON
            .parse(
                window.atob(
                    payload.replace(/-/g, '+')
                        .replace(/_/g, '/')))
        return decodedPayload.sub
    } else {
        return null
    }
}


