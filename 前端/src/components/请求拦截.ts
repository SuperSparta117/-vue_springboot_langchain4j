import axios from 'axios'
import { message } from 'ant-design-vue'
import { 服务器请求地址 } from  '@/stores/全局变量.js'

// 创建 Axios 实例
const myAxios = axios.create({
  baseURL: 服务器请求地址,
  timeout: 60000,
  withCredentials: true,//一定要写，否则无法在发请求时携带 Cookie，也就无法在后端获取到用户的登录状态了
})


// 响应拦截器的应用场景:我们需要对接口的 通用响应 进行统一处理，比如从 response 中取出 data;或者根据 code 去集中处理错误。这样不用在每个接口请求中都去写相同的逻辑。
// 全局请求拦截器
myAxios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    return config
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error)
  },
)

// 全局响应拦截器
myAxios.interceptors.response.use(
// 比如可以在全局响应拦截器中，读取出结果中的 data，并校验 code 是否合法，如果是未登录状态，则自动登录。
  function (response) {
    const { data } = response
    // 未登录
    if (data.code === 40100) {
      // 不是获取用户信息的请求，并且用户目前不是已经在用户登录页面，则跳转到登录页面
      if (
        !response.request.responseURL.includes('user/get/login') &&
        !window.location.pathname.includes('/user/login')
      ) {
        message.warning('请先登录')
        window.location.href = `/user/login?redirect=${window.location.href}`
      }
    }
    return response
  },
  function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error)
  },
)

export default myAxios
