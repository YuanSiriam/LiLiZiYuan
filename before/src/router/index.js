import {createRouter, createWebHashHistory} from "vue-router";
import HomeView from "@/views/HomeView.vue";


const routes = [
    {//首页
        path: '/',
        name: 'Home',
        component: HomeView
    },
    {//关于
        path: '/about',
        name: 'About',
        component: () => import('../views/AboutView.vue')
    }

]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

export default router
