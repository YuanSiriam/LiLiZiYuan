<script setup>
import {nextTick, onMounted, reactive, ref, watch, computed} from 'vue';

const jwtToken = localStorage.getItem("jwt")
const userId = getUserIdFromJwt(jwtToken)

// WebSocket连接
const socket = ref(null);
const receivedMessages = ref([]);

// 连接参数
const url = '/serve/ws/message';
let reconnectAttempts = 0;
let heartbeatTimer;
const HEARTBEAT_INTERVAL = 2; // 心跳间隔，单位毫秒

// 连接打开时
const handleOpen = (event) => {
  console.log('WebSocket connection opened:', event);
  reconnectAttempts = 0; // 重置重连尝试次数
};


// 连接关闭时
const handleClose = (event) => {
  socket.value = null
  console.log('WebSocket connection closed:', event);
};

// 连接错误时
const handleError = (event) => {
  console.error('WebSocket error:', event);
};


// 创建WebSocket连接
const connect = () => {

  console.log(userId)
  socket.value = new WebSocket(`${url}?userId=${userId}`)
  socket.value.addEventListener('open', handleOpen, startHeartbeat)
  socket.value.addEventListener('message', handleMessage)
  socket.value.addEventListener('close', handleClose, stopHeartbeat)
  socket.value.addEventListener('error', handleError)
};

// 通过jwt获取userId
function getUserIdFromJwt(jwt) {
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

// 设置心跳检测
const sendHeartbeat = () => {
  console.log("发送心跳包")
  socket.value.send(JSON.stringify({type: 'heartbeat'}));
};
const startHeartbeat = () => {
  heartbeatTimer = setInterval(sendHeartbeat, HEARTBEAT_INTERVAL);
};
const stopHeartbeat = () => {
  clearInterval(heartbeatTimer);
};

/**
 * 接收消息
 * @param event
 */
const handleMessage = (event) => {
  const message = JSON.parse(event.data);
  serveArray.push(message);
};

/**
 * 发送消息
 */

class SendToServe {
  id
  userId
  username
  content
  color
  createTime

  constructor() {
    this.id = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15)
    this.userId = userId
    this.color = ''
    this.createTime = new Date().toISOString()
  }
}

let sendMsg = reactive({
  username: '',
  content: '',
})

let sendDisabled = ref(false)
const sendMessage = () => {

  if (!sendDisabled.value) {
    sendDisabled.value = true;

    // 处理点击事件的代码
    if (sendMsg.content.trim() === '' && sendMsg.username.trim() === '') {
      alert("别空着框biubiu射啊，大哥")
    } else {
      let sendToServe = new SendToServe()
      sendToServe.username = sendMsg.username
      sendToServe.content = sendMsg.content
      console.log(sendToServe)
      socket.value.send(JSON.stringify(sendToServe));
      console.log("发送至服务器")

    }
    setTimeout(() => {
      // 恢复按钮为可点击状态
      sendDisabled.value = false;
    }, 1000);
  }
};
/**
 * 开启/关闭webSocket服务
 * @type {Ref<UnwrapRef<boolean>>}
 */
let flag = ref(false)
let biubiuFlag = ref(false)
const connectionControl = () => {
  if (userId !== null) {
    flag.value = !flag.value
    if (flag.value) {
      console.log("开始连接")
      try{
        connect()
      }catch (e){
        alert("连接服务器失败，请联系微信18538319447")
      }
      alertList.message = '连接成功'
      if (alertRef.value && alertRef.value.showAlert) {
        alertRef.value.showAlert()
      }
    } else {
      console.log("断开连接")
      socket.value.close()
    }
  } else {
    alert("请先登录")
  }
};

/**
 * 13个轨道的初始化
 */
onMounted(() => {
  for (let i = 0; i < trackCount; i++) {
    let track = new Track()
    track.offset = i * trackHeight
    tracks.push(track)
  }
})
/**
 * 监听服务器返回的消息列队
 */
let serveArray = reactive([
  {
    id: '',
    userId: '',
    username: '',
    color: '',
    content: '',
    createTime: ''
  },
])
watch(serveArray, () => {
  handleServeArrayChange();
}, {deep: true})
// 定义一个函数，当数组长度大于0时被调用
const handleServeArrayChange = () => {
  if (serveArray.length > 0) {
    // 在这里执行你的逻辑
    console.log('数组长度大于0，开始执行逻辑...', serveArray);
    // 例如，你可以遍历数组并执行某些操作
    while (serveArray.length > 0) {
      let index = 0
      addBullet(serveArray[index])
      serveArray.splice(0, 1)
    }

  }
};

/**
 * 对服务器返回的消息进行封装，上传到数组bullets中供页面循环遍历
 */
function addBullet() {
  // 将第一个值赋值给bullet对象并存储在等待队列
  const firstElement = serveArray[0]
  let bullet = new Bullet()
  Object.assign(bullet, firstElement)
  /**
   * 加入数组之前为数组设置好轨道
   */
  bullet.track = randomSelect()
  bullets.push(bullet)
  nextTick(() => {
    run()
  })
}

/**
 * 分配一个随机可用轨道
 * @returns {Track}
 */
const randomSelect = () => {
  // 每次调用时都重新计算可用轨道列表
  let availableTracks = tracks.filter(track => !track.disable);
  // 如果没有可用的Track
  if (availableTracks.length === 0) {
    let forTracks = setInterval(() => {
      availableTracks = tracks.filter(track => !track.disable)
      if (availableTracks > 0) {
        forTracks = null
      }
    }, 500)
  }
  // 随机选择一个Track
  const index = Math.floor(Math.random() * availableTracks.length);
  return availableTracks[index]
}

/**
 * 使弹幕进行活动
 * 活动结束后销毁
 */
function run() {
  bullets.forEach((bullet) => {
    if (bullet.status === 'waiting') {
      bullet.status = 'running'
      bullet.track.disable = true

      let w1 = document.getElementById('scrollingContainer').getBoundingClientRect().width
      let w2 = document.getElementById(bullet.id).offsetWidth

      const delay = (w2 / (w1 + w2)) * 5 * 1000
      console.log(waitingQueue)
      setTimeout(() => {
        bullet.track.disable = false
      }, delay)
    }
  })
}

function done(bullet) {
  bullet.status = 'done'
  nextTick(() => {
    const index = bullets.indexOf(bullet)
    if (index > -1) {
      bullets.splice(index, 1)
    }
  })
}

const trackHeight = 59
const trackCount = 12

let bullets = reactive([])
let tracks = reactive([])
let waitingQueue = reactive([])

class Bullet {
  id
  userId
  username
  color
  content
  createTime

  status = 'waiting'
  track
}

class Track {
  offset = 0
  disable = false
}

</script>


<template>
  <div>


    <div class="flex justify-center items-center min-h-screen">

      <div class="flex flex-col items-center justify-center w-screen h-screen overflow-hidden bg-transparent">
        <div id="scrollingContainer" class="relative w-full overflow-hidden z-40" style="height: 80%">
          <div v-for="track in tracks"
               style="height: 59px;"
          >

          </div>

          <div v-for="bullet in bullets"
               :id="bullet.id"
               :key="bullet.id"
               :style="{top: bullet.track.offset + 'px'}"
               :class="['bullet-'+bullet.status]"
               class="text-3xl whitespace-nowrap absolute font-bold to-purple-600 text-white "
               @animationend="done(bullet)"
          >
            {{ bullet.username }}射了: {{ bullet.content }}
          </div>
        </div>
      </div>
      <p :class="{ 'opacity-50': flag }"
         class="absolute left-1/2 transform -translate-x-1/2 -translate-y-1/2  font-black tracking-wider text-9xl"
         style="top: 155%">是兄弟就biu</p>
      <input type="checkbox" @click="connectionControl"
             class="absolute transform -translate-x-1/2 -translate-y-1/2 toggle toggle-lg z-50"
             style="left: 65.66%;top: 150%" v-model="flag"/>
      <button v-show="flag" @click="biubiuFlag = !biubiuFlag" class="fixed bottom-16 left-0 right-0 z-50">👉biubiu ->
      </button>
      <!--弹幕输入框-->
      <div>
        <transition name="fade">
          <div class="opacity-65">
            <div v-if="biubiuFlag && flag" id="bottom-modal" class="bg-black fixed bottom-0 left-0 right-0 -z-50 pb-12 pl-0">
              <form>
                <ul>
                  <li class="fixed pl-72 z-50">
                    <input type="text" v-model="sendMsg.username"
                           class="input input-bordered flex items-center gap-2"
                           placeholder="名称">
                  </li>
                  <li class="fixed z-40" style="padding-left: 503px">
                    <input type="text" v-model="sendMsg.content"
                           class="input input-bordered flex items-center gap-2" style="width: 650px"
                           placeholder="内容">
                  </li>
                  <li class="fixed z-30 " style="padding-bottom: 30px">
                    <button v-if="jwtToken" :disabled="sendDisabled" @click="sendMessage" class="justify-end btn border-l-blue-300"
                            style="border-left-width: 1157px">biubiubiu->
                    </button>
                    <button @click="biubiuFlag = !biubiuFlag" class="justify-end btn border-r-amber-100"
                            style="border-right-width: 232px"> back - >
                    </button>

                  </li>
                </ul>

              </form>

            </div>
          </div>

        </transition>
      </div>
    </div>
    {{ receivedMessages }}
  </div>

</template>

<style scoped lang="less">
// 装填
.bullet-waiting {
  left: 100%;
}

// 已完成
.bullet-done {
  left: 0;
  transform: translate3d(-100%, 0, 0);
}

// 运动中
@keyframes running {
  from {
    left: 100%;
    transform: translate3d(0, 0, 0);
  }
  to {
    left: 0;
    transform: translate3d(-100%, 0, 0);
  }
}

.bullet-running {
  animation: running 5s linear;
}

</style>