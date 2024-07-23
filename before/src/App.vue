<template>

  <!-- 背景图层 -->


  <div>

    <div class="bg-scroll flex flex-col min-h-screen" :style="dynamicBackgroundStyle">
      <!-- 导航栏 -->
      <div class="navbar bg-primary text-primary-content">
        <!-- 下拉菜单 -->
        <div class="navbar-start">
          <div class="dropdown">
            <div tabindex="0" role="button" class="btn btn-ghost text-xl">
              <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor">
                <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M4 6h16M4 12h16M4 18h7"/>
              </svg>
            </div>
            <ul
                tabindex="0"
                class="bg-blue-400 menu menu-sm dropdown-content rounded-box z-[1] mt-3 w-75 p-2 shadow">
              <li><a>
                <label v-if="!jwtToken" for="login-drawer" class="btn btn-wide btn-primary">登录</label>
              </a></li>
              <li><a>
                <label for="user-drawer" class="btn btn-wide btn-primary">账户</label>
              </a></li>
              <li><a>
                <label v-if="jwtToken" @click="logout" for="logout-drawer" class="btn btn-wide btn-primary">登出</label>
              </a></li>

            </ul>
          </div>
        </div>
        <div class="navbar-center">
          <a class="btn btn-ghost text-xl">LiLiYSP</a>
        </div>
        <div class="navbar-end">
          <!-- 搜索框 -->
          <div class="navbar-end">
            <input type="text" class="btn btn-ghost btn-block border-b-black border-b-4" placeholder="Search">
            </input>
          </div>

          <!-- 消息提示框 -->
          <button class="btn btn-ghost btn-circle">
            <div class="indicator">
              <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor">
                <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"/>
              </svg>

              <span class="badge badge-xs badge-primary indicator-item"></span>
            </div>
          </button>
        </div>
      </div>

      <nav>
        <router-link to="/">首页</router-link>
        |
        <router-link to="/about">书籍商城</router-link>
      </nav>
      <router-view/>


      <!--  抽屉：登录抽屉 -->
      <div class="drawer">
        <input id="login-drawer" type="checkbox" class="drawer-toggle"/>
        <div class="drawer-content">
          <!-- Page content here -->
          <label for="login-drawer"></label>
        </div>
        <div class="drawer-side">
          <label for="login-drawer" aria-label="close sidebar" class="drawer-overlay"></label>

          <ul class="menu bg-base-200 text-base-content min-h-full w-80 p-4">
            <form @submit="loginSubmit">
              <div class="invisible ...">02</div>
              <label class="input input-bordered flex items-center gap-2">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 16 16"
                    fill="currentColor"
                    class="h-4 w-4 opacity-70">
                  <path
                      d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6ZM12.735 14c.618 0 1.093-.561.872-1.139a6.002 6.002 0 0 0-11.215 0c-.22.578.254 1.139.872 1.139h9.47Z"/>
                </svg>
                <input type="text" v-model="loginUser.username" class="grow" placeholder="mail"/>
              </label>
              <div class="invisible ...">02</div>
              <label class="input input-bordered flex items-center gap-2">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 16 16"
                    fill="currentColor"
                    class="h-4 w-4 opacity-70">
                  <path
                      fill-rule="evenodd"
                      d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z"
                      clip-rule="evenodd"/>
                </svg>
                <input type="password" v-model="loginUser.password" class="grow" placeholder="Password"/>
              </label>
              <div class="invisible ...">02</div>
              <!-- 验证码 -->
              <div class="flex items-center gap-2">
                <label class="relative flex input items-center input-bordered pl-10 w-1/2">
                  <input type="captcha" v-model="loginUser.code" class="grow" placeholder="captcha"/>
                </label>
                <img @click="getCaptcha" :src="captchaImage" alt="captcha" class="flex gap-2 w-1/2"/>
              </div>

              <div class="invisible ...">02</div>
              <button type="submit" class="btn btn-ghost text-xl w-full">登录</button>
            </form>
          </ul>

        </div>
      </div>
      <div class="fixed bottom-0 left-0 right-0 flex justify-center items-end p-4">

        <AlertComponent :="alertList" ref="alertRef" />
      </div>

    </div>

  </div>

</template>

<script setup>
// 背景图层配置
import {computed, onMounted, reactive, ref} from "vue";
import background1 from '@/assets/img/background1.png'
import axios from "axios";
import AlertComponent from "@/components/AlertComponent.vue";

// 获取jwt
const jwtToken = localStorage.getItem("jwt")

// 验证码image
const captchaImage = ref()

// 计算背景样式对象
const background_url = ref(background1)
const dynamicBackgroundStyle = computed(() => ({
  'background-image': `url(${background_url.value})`,
  'background-position': 'center center',
  'background-size': 'cover',
  'background-repeat': 'no-repeat',
  'background-attachment': 'fixed',
}));

// 弹窗组件
const alertRef = ref(null)
const alertList = reactive({
  message: '',
  duration: 3000,
})

// 登陆对象
const user = reactive({
  username: ''
})

// 登录请求参数
const loginUser = {
  username: '',
  password: '',
  code: '',
  key: ''
};

// 登录按钮点击事件
const loginSubmit = () => {
  if (!loginUser.username || !loginUser.password){
    alertList.message = '请输入用户名或密码'
    if (alertRef.value && alertRef.value.showMessage){
      alertRef.value.showMessage()
    }
  }else {
    console.log(loginUser.username)
    axios.post("/serve/user/login", loginUser, {
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => {

      if (response.data.code === 200) {
        // 获取响应头中jwt,并存储在localStorage中
        localStorage.setItem("jwt", response.headers.authorization)

        // 登录成功
        alertList.message = '登录成功'
        if (alertRef.value && alertRef.value.showMessage){
          alertRef.value.showMessage()
        }
        setTimeout(() => {
          window.location.href = "/"
        }, 1500)
      }

    }).catch(error => {
      // 登录失败
      alertList.message = '用户名或密码错误'
      if (alertRef.value && alertRef.value.showMessage){
        alertRef.value.showMessage()
      }
      console.log("login failed",error)
    })
  }

}

// 登出按钮点击事件
const logout = () => {
  localStorage.removeItem("jwt")
  const jwt = ref(localStorage.getItem('jwt'))
  if (jwt){
    if (alertRef.value && alertRef.value.showMessage){
      alertList.message = '退出成功'
      alertRef.value.showMessage()
    }
    setTimeout(() => {
      window.location.href = "/"
    }, 1500)
  }else {
    if (alertRef.value && alertRef.value.showMessage){
      alertList.message = '退出失败'
      alertRef.value.showMessage()
    }
  }

}

// 获取验证码
onMounted(getCaptcha)
function getCaptcha() {
  axios.get("/serve/user/captcha").then(response => {
    loginUser.key = response.data.key
    captchaImage.value = response.data.captchaImage
  })
}

</script>



<style lang="scss" scoped>
</style>