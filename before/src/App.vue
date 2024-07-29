<template>

  <div>
    <div class="bg-scroll flex flex-col min-h-screen" :style="dynamicBackgroundStyle">
      <!-- 导航栏 -->
      <div class="fixed top-0 navbar bg-primary text-primary-content">
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
                <h1 class="text-lg font-bold">Hello! {{ user.userId }}</h1>
              </a></li>
              <li><a>
                <label v-if="!jwtToken"
                       class="btn btn-wide border-b-4 border-b-amber-50">注册</label>
              </a></li>
              <li><a>
                <label v-if="!jwtToken" @click="getCaptcha" for="login-drawer"
                       class="btn btn-wide btn-primary">登录</label>
              </a></li>
              <li><a>
                <label v-if="jwtToken" onclick="uploadPost.showModal()"
                       for="logout-drawer" class="btn btn-wide border-b-4 border-b-amber-50">发帖子</label>
              </a></li>
              <li><a>
                <label @click="showPost" onclick="postDisplay.showModal()"
                       for="user-drawer" class="btn btn-wide border-b-4 border-b-amber-50">是兄弟就来看 <p class="text-warning">帖子</p>
                </label>
              </a></li>
              <li><a>
                <label v-if="jwtToken" @click="logout" for="logout-drawer" class="btn btn-wide border-b-4 border-b-amber-50">登出</label>
              </a></li>

            </ul>
          </div>
        </div>
        <div class="navbar-center">
          <a class="btn btn-ghost text-xl">LiLiYSC</a>
        </div>
        <div class="navbar-end">
          <!-- 搜索框 -->
          <div class="navbar-end">
            <input type="text" class="btn btn-ghost btn-block border-b-black border-b-4" placeholder="Search">
            </input>
          </div>

          <!-- 消息提示框 -->
          <button @click="showMessage" onclick="messageDisplay.showModal()" class="btn btn-ghost btn-circle">
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
      <!-- 网站名称 -->
      <div class="flex justify-center items-center min-h-screen">
        <p class="font-black tracking-wider text-9xl">LiLiYSC</p>
      </div>
      <!-- 分割线-->
      <div class="divider divider-warning">⬇</div>
      <nav>
        <scrolling/>

        <router-link to="/about"></router-link>
      </nav>
      <router-view/>
      <!-- 背景图层 -->


      <!-- 抽屉：登录抽屉 -->
      <div v-if="!jwtToken" class="drawer">
        <input id="login-drawer" type="checkbox" class="drawer-toggle"/>
        <div class="drawer-content">
          <!-- Page content here -->
          <label for="login-drawer"></label>
        </div>
        <div class="drawer-side">
          <label for="login-drawer" aria-label="close sidebar" class="drawer-overlay"></label>

          <ul class="menu bg-base-200 text-base-content min-h-full w-80 p-4">
            <form>
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
              <button @click="loginDisable" :disabled="loginDisabled" type="submit" class="btn btn-ghost text-xl w-full">登录</button>
            </form>
          </ul>

        </div>
      </div>

      <!-- 消息展示框 -->
      <div>
        <dialog id="messageDisplay" class="modal">
          <div class="modal-box h-5/6 w-11/12 max-w-5xl">
            <h3 class="card-title p-4">{{ displayList.title }}</h3>
            <!-- 循环体-->
            <div>
              <ul>
                <li v-for="message in displayList.messages" :key="message.messageId">
                  <div class="chat chat-start">
                    <div class="chat-header">
                      system
                      <time class="text-xs opacity-50">{{ message.createTime }}</time>
                    </div>
                    <div class="chat-bubble">{{ message.content }}</div>
                    <!-- 是否看过 -->
                    <div class="chat-footer opacity-50"></div>
                  </div>
                </li>
              </ul>
            </div>

            <div class="right-10 top-6 fixed">
              <form method="dialog">
                <button class="btn btn-wide">按Esc退出</button>
              </form>
            </div>
          </div>
        </dialog>
      </div>
      <!-- 论坛展示框 -->
      <div>
        <dialog id="postDisplay" class="modal">
          <div class="modal-box h-fit w-11/12 max-w-5xl overflow-y-auto">
            <!-- 按钮-->
            <div class="right-10 top-6 fixed">
              <form method="dialog">
                <button @click="zero" class="btn btn-wide">按Esc退出</button>
              </form>
            </div>
            <!-- 标题 -->
            <h3 class="card-title p-4">帖子</h3>
            <!-- 帖子遍历-->
            <div>
              <ul class="flex flex-wrap gap-4">
                <li v-for="post in displayList.messages" :key="post.postId"
                    class="card bg-base-100 image-full max-w-4xl shadow-xl">
                  <transition @before-enter="" @leave="">
                    <div
                        :class="['max-w-4xl', 'card-body',post.expanded ? 'w-[896px] transition-all duration-300 ease-in-out' : '',post.expanded ? 'overflow-visible' : '']">
                      <h2 class="card-title">{{ post.title }}</h2>
                      <div class="chat-header flex flex-wrap gap-4">
                        {{ post.username }}
                        <time class="text-xs opacity-50">{{ post.createTime }}</time>
                      </div>
                      <pre v-if="!post.expanded" class="overflow-hidden line-clamp-3">{{ post.content }}</pre>
                      <pre v-else class="max-w-4xl whitespace-pre-wrap">{{ post.content }}</pre>
                      <!-- 评论遍历-->
                      <div v-if="post.expanded">
                        <!--分割线-->
                        <div class="divider"></div>
                        <button v-if="jwtToken" @click="bottomInputCommentShow('comment',post.postId)"
                                class="text-sm opacity-50  pl-96">评论此帖
                        </button>
                        <h2 class="card-title">评论</h2>
                        <ul>
                          <li v-for="comment in displayComment.messages" :key="comment.commmentId">
                            <div class="chat chat-start">
                              <div class="chat-header">
                                {{ comment.username }}
                                <time class="text-xs opacity-50">{{ comment.createTime }}</time>
                                <button v-if="jwtToken" @click="bottomInputReplyShow('reply',post.postId,comment.commentId,'admin')"
                                        class="text-sm opacity-50 pl-2">回复评论
                                </button>
                              </div>
                              <div class="chat-bubble chat-bubble-success">{{ comment.content }}</div>
                              <!-- 回复 -->
                              <div class="collapse chat-footer">
                                <input type="checkbox"/>
                                <div class="collapse-title  opacity-25">
                                  {{ comment.commentCount }}条回复
                                </div>
                                <div class="collapse-content">
                                  <!-- 回复遍历 -->
                                  <ul>
                                    <li v-for="reply in displayReply.messages.filter(reply => reply.toCommentId === comment.commentId)"
                                        :key="reply.replyId">
                                      <div v-if="reply.toReplyUsername === 'admin'" class="chat chat-start">
                                        <div class="chat-header">
                                          {{ reply.username }}
                                          <time class="text-xs opacity-50">{{ reply.createTime }}</time>
                                          <button v-if="jwtToken"
                                              @click="bottomInputReplyShow('reply',post.postId,comment.commentId,reply.username)"
                                              class="text-sm opacity-50 pl-2">回复他的回复
                                          </button>
                                        </div>
                                        <div class="chat-bubble chat-bubble-accent">
                                          {{ reply.content }}
                                        </div>
                                      </div>
                                      <div v-else class="chat chat-end">
                                        <div class="chat-header">
                                          <button
                                              @click="bottomInputReplyShow('reply',post.postId,comment.commentId,reply.username)"
                                              class="text-sm opacity-50 pl-2">回复他的回复
                                          </button>
                                          {{ reply.username }} 回复 {{ reply.toReplyUsername }}
                                          <time class="text-xs opacity-50">{{ reply.createTime }}</time>
                                        </div>
                                        <div class="chat-bubble chat-bubble-accent">
                                          {{ reply.content }}
                                        </div>
                                      </div>

                                    </li>
                                  </ul>
                                  <div class="divider"></div>

                                </div>
                              </div>
                            </div>
                          </li>
                        </ul>

                      </div>

                      <!-- 进入按钮-->
                      <div class="card-actions justify-end">
                        <button @click="showComment(post)" class="btn btn-primary">{{ post.button }}-></button>
                      </div>
                    </div>

                  </transition>

                </li>
              </ul>
            </div>

          </div>
          <!--评论/回复输入框-->
          <div>
            <transition name="fade">
              <div class="opacity-65">
                <div v-if="bottomInput.isShow" id="bottom-modal" class="bg-black fixed bottom-0 left-0 right-0 -z-50 pb-12 pl-0">
                  <form>
                    <ul>
                      <li class="fixed pl-72 z-50">
                        <input type="text" v-model="commentOrReply.username"
                               class="input input-bordered flex items-center gap-2"
                               placeholder="名称">
                      </li>
                      <li class="fixed z-40" style="padding-left: 503px">
                        <input type="text" v-model="commentOrReply.content"
                               class="input input-bordered flex items-center gap-2" style="width: 650px"
                               placeholder="内容">
                      </li>
                      <li class="fixed" style="padding-left: 0px;padding-bottom: 30px">
                        <button :disabled="uCommentOrReplyDisabled" @click="uCommentOrReply()" class="justify-end btn border-x-blue-300"
                                style="border-left-width: 1157px;border-right-width: 364px">提交-></button>
                      </li>
                    </ul>

                  </form>

                </div>
              </div>

            </transition>
          </div>
        </dialog>
      </div>
      <!-- 发帖框 -->
      <div>
        <dialog id="uploadPost" class="modal">
          <div class="modal-box h-fit w-11/12 max-w-5xl overflow-y-auto">
            <!-- 按钮-->
            <div class="right-10 top-6 fixed">
              <form method="dialog">
                <button class="btn btn-wide">按Esc退出</button>
              </form>
            </div>
            <div class="artboard artboard-horizontal phone-5">
              <div class="pt-12">
                <form action="">
                  <div class="pl-3">
                    <ul>
                      <li>
                        <div class="pl-64 opacity-65 text-xs">有无后期不知，但后期据此分类</div>
                        <input type="text" v-model="post.title" class="btn-wide btn border-l-8 border-l-amber-100"
                               placeholder="Title">
                        <input type="text" v-model="post.topic" class="btn-wide btn border-l-8 border-l-amber-100"
                               placeholder="Topic 请随意">
                      </li>
                      <li class="pt-14 flex">
                        <textarea v-model="post.content" class="textarea textarea-bordered w-3/4 h-64"
                                  placeholder="Bio"></textarea>
                        <div class="pt-52 pl-28">
                          <button type="submit" :disabled="uPostDisabled" @click="uPostDisable" class="justify-end btn btn-primary">提交-></button>
                        </div>
                      </li>
                    </ul>
                  </div>

                </form>
              </div>
            </div>


          </div>
        </dialog>
      </div>
      <!-- 消息警告框 -->
      <div>
        <AlertComponent class="fixed bottom-0 left-0 right-0 flex justify-center items-end p-4" :="alertList" ref="alertRef"/>
      </div>
    </div>
  </div>

</template>

<script setup>

// 背景图层配置
import {computed, reactive, ref, onBeforeUnmount, onMounted} from "vue";
import background1 from '@/assets/img/background1.png'
import axios from "axios";
import AlertComponent from "@/components/AlertComponent.vue";
import Scrolling from "@/components/duplex/Scrolling.vue";
import {getUserIdFromJwt} from "@/main.js";

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

/**
 * 组件
 * @type {Reactive<{title: string}>}
 */
// 展示框组件
const displayList = reactive({
  title: '',
  messages: []
})
const displayComment = reactive({
  messages: []
})
const displayReply = reactive({
  messages: []
})

// 弹窗组件
const alertRef = ref(null)
const alertList = reactive({
  message: '',
  duration: 3000,
})

// 登陆对象
const user = reactive({
  userId: getUserIdFromJwt(jwtToken)
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
  if (!loginUser.username || !loginUser.password) {
    alertList.message = '请输入用户名或密码'
    if (alertRef.value && alertRef.value.showAlert) {
      alertRef.value.showAlert()
    }
  } else {
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
        alertList.message = '登录成功，1.5秒后刷新页面'
        if (alertRef.value && alertRef.value.showAlert) {
          alertRef.value.showAlert()
          setTimeout(() => {
            window.location.href = "/"
          }, 1500)
        }else {
          alert('登录失败')
        }
      }

    }).catch(error => {
      // 登录失败
      alertList.message = '用户名或密码或验证码错误'
      if (alertRef.value && alertRef.value.showAlert) {
        alertRef.value.showAlert()
      }
      console.log("login failed", error)
    })
  }
}
let loginDisabled = ref(false)
function loginDisable(){
  if (!loginDisabled.value) {
    loginDisabled.value = true;
    // 处理点击事件的代码
    loginSubmit()
    setTimeout(() => {
      // 恢复按钮为可点击状态
      loginDisabled.value = false;
    }, 1500);
  }
}

// 登出按钮点击事件
const logout = () => {
  localStorage.removeItem("jwt")
  const jwt = ref(localStorage.getItem('jwt'))
  if (jwt) {
    if (alertRef.value && alertRef.value.showAlert) {
      alertList.message = '退出成功'
      alertRef.value.showAlert()
    }
    setTimeout(() => {
      window.location.href = "/"
    }, 1500)
  } else {
    if (alertRef.value && alertRef.value.showAlert) {
      alertList.message = '退出失败'
      alertRef.value.showAlert()
    }
  }

}

// 消息按钮点击事件
const showMessage = () => {
  displayList.title = "Message"
  axios.get("/serve/message/show", {
    params: {userId: user.userId}, // 使用params传递userId
  }).then(response => {
    if (response.data.code === 0) {
      displayList.messages = response.data.array
    } else {
      console.log("show message failed")
    }
  })
}

// 畅所欲言点击事件
const showPost = () => {
  displayList.title = "Post"
  axios.get("/serve/post/showAll").then(response => {
    if (response.data.code === 0) {
      displayList.messages = response.data.array
      console.log(displayList.messages)
    } else {
      console.log("show message failed")
    }
  })
}

// 获取验证码
function getCaptcha() {
  axios.get("/serve/user/captcha").then(response => {
    loginUser.key = response.data.key
    captchaImage.value = response.data.captchaImage
  })
}

// 展开评论
const commentButtonCount = ref(0)

function showComment(post) {
  if (commentButtonCount.value >= 1 && post.button === 'looklook') {
    alertList.message = '请结束观看其他评论...不能一心二用哟~'
    if (alertRef.value && alertRef.value.showAlert) {
      alertRef.value.showAlert()
    }
    return
  }
  if (post.button === 'looklook') {
    console.log("打开")
    commentButtonCount.value = 1
    post.expanded = !post.expanded
    post.button = 'back'
    axios.get(`/serve/post/${post.postId}`).then(
        response => {
          if (response.data.code === 0) {
            displayComment.messages = response.data.array
          } else {
            console.log("show comment failed")
          }
        }
    )
    axios.get(`/serve/post/${post.postId}/getReply`).then(
        response => {
          if (response.data.code === 0) {
            displayReply.messages = response.data.array
            console.log(displayReply.messages)
          } else {
            console.log("show reply failed")
          }
        }
    )
  } else {
    post.expanded = !post.expanded
    commentButtonCount.value = 0
    post.button = 'looklook'
  }
}

// 监听按钮事件
const handleKeyDown = (event) => {
  if (event.key === 'Escape') {
    console.log('Escape key pressed');
    commentButtonCount.value = 0
  }
};
onMounted(() => {
  window.addEventListener('keydown', handleKeyDown);
});
onBeforeUnmount(() => {
  window.removeEventListener('keydown', handleKeyDown);
});

function zero() {
  commentButtonCount.value = 0
}

// 发布帖子事件
const post = reactive({
  title: '',
  userId: user.userId,
  topic: '',
  content: ''
})
let uPostDisabled = ref(false)
function uPostDisable(){
  if (!uPostDisabled.value) {
    uPostDisabled.value = true;
    // 处理点击事件的代码
    axios.post("/serve/post/upload", post).then(
        response => {
          if (response.data.code === 0) {
            alertList.message = '发布成功'
            if (alertRef.value && alertRef.value.showAlert) {
              alertRef.value.showAlert()
            }
            setTimeout(() => {
              window.location.href = "/"
            }, 1500)
          } else {
            alertList.message = '发布失败'
            if (alertRef.value && alertRef.value.showAlert) {
              alertRef.value.showAlert()
            }
          }
        }
    )
    setTimeout(() => {
      // 恢复按钮为可点击状态
      uPostDisabled.value = false;
    }, 1000);
  }
}


// 底部输入框  评论/回复点击事件
const bottomInput = reactive({
  isShow: false,
  replyOrComment: '',
  toPostId: '',
  content: '',
  toCommentId: '',
  toReplyUsername: ''
})

function bottomInputCommentShow(which, toPostId) {
  bottomInput.isShow = !bottomInput.isShow
  bottomInput.replyOrComment = which
  bottomInput.toPostId = toPostId
}

function bottomInputReplyShow(which, toPostId, toCommentId, toReplyUsername) {
  bottomInput.isShow = !bottomInput.isShow
  bottomInput.replyOrComment = which
  bottomInput.toReplyUsername = toReplyUsername
  bottomInput.toCommentId = toCommentId
  bottomInput.toPostId = toPostId
}

// 发布评论或回复事件
const commentOrReply = reactive({
  username: '',
  content: '',
  userId: user.userId
})

let uCommentOrReplyDisabled = ref(false)
function uCommentOrReply(){
  if (!loginDisabled.value) {
    loginDisabled.value = true;
    // 处理点击事件的代码
    if (bottomInput.replyOrComment === 'comment') {
      console.log("上传帖子", bottomInput.toPostId)
      bottomInput.isShow = !bottomInput.isShow
      axios.post(`/serve/post/${bottomInput.toPostId}/uComment`, commentOrReply).then(
          response => {
            if (response.data.code === 0) {
              alertList.message = '评论成功'
              if (alertRef.value && alertRef.value.showAlert) {
                alertRef.value.showAlert()
              }
            } else {
              alertList.message = '评论失败'
              if (alertRef.value && alertRef.value.showAlert) {
                alertRef.value.showAlert()
              }
            }
          }
      )
    } else if (bottomInput.replyOrComment === 'reply') {
      console.log("上传回复", bottomInput.toPostId)
      bottomInput.isShow = !bottomInput.isShow
      axios.post(`/serve/post/${bottomInput.toPostId}/${bottomInput.toCommentId}/${bottomInput.toReplyUsername}/uReply`, commentOrReply).then(
          response => {
            if (response.data.code === 0) {
              alertList.message = '评论成功'
              if (alertRef.value && alertRef.value.showAlert) {
                alertRef.value.showAlert()
              }
            } else {
              alertList.message = '评论失败'
              if (alertRef.value && alertRef.value.showAlert) {
                alertRef.value.showAlert()
              }
            }
          }
      )
    }
    setTimeout(() => {
      // 恢复按钮为可点击状态
      loginDisabled.value = false;
    }, 1000);
  }
}

</script>


<style lang="scss">

</style>