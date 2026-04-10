//  编写全局权限校验核心文件access/index.ts ，其实就是路由拦截器
// 逻辑如下:
// 1.首先判断页面是否需要登录权限，如果不需要，直接放行。
// 2.如果页面需要登录权限
// 3.如果用户未登录，则跳转到登录页面。
// 4.如果已登录，判断登录用户的权限是否符合要求，否则跳转到 401 无权限页面。

import router from '@/router'
import { useLoginUserStore } from '@/stores/loginUser'
import ACCESS_ENUM from './accessEnum'
import checkAccess from './checkAccess'

//  把用户状态分成3种，而不是简单2种:
// 1.还没查过
// 特征:没有userRole
// 含义:首次进入页面，前端还不知道用户是否登录
// 2.查过了，确认没登录
// 特征:userRole === 'notLogin
// 含义:已经请求过后端了，后端说当前没
// 有登录用户
// 3.查过了，已经登录
// 特征:
// 或admin
// userRole === 'user'
// 含义:后端返回了真实登录用户


router.beforeEach(async (to, from, next) => { //“不要把useLoginUserstore()写在文件顶层。要把它写进 beforeEach回调里，等路由守卫真正运行时再拿 store，这样才能保证 Pinia 已经初始化好了。”



  const loginUserStore = useLoginUserStore()
  let loginUser = loginUserStore.loginUser
  console.log('登陆用户信息', loginUser)

  // // 如果之前没登陆过，自动登录（登录状态自动恢复）
  if (!loginUser || !loginUser.userRole) {//第一次进入浏览器访问该页面会有的情况，pinia是空的
  // 加 await 是为了等用户登录成功之后，再执行后续的代码
  await loginUserStore.fetchLoginUser();//去后端查当前登录的是谁，拿到用户信息后会更新 loginUserStore.loginUser 的值
  loginUser = loginUserStore.loginUser;//这样如果本地cookie有上一次session会话自动存的sessionid，就能自动拿到登录用户信息了，这样后面不用再登录。
  }

  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN
  // 要跳转的页面必须要登陆
  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    // 如果没登陆，跳转到登录页面
    if (!loginUser || !loginUser.userRole || loginUser.userRole === ACCESS_ENUM.NOT_LOGIN) {
      next(`/user/login?redirect=${to.fullPath}`)
      return
    }
    // 如果已经登陆了，但是权限不足，那么跳转到无权限页面
    if (!checkAccess(loginUser, needAccess)) {
      next('/noAuth')
      return
    }
  }
  next()
})
