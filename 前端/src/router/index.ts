import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import HomeView from '../Pages/HomePage.vue'
import UserLoginPage from '@/Pages/用户相关/UserLoginPage.vue'
import UserRegisterPage from '@/Pages/用户相关/UserRegisterPage.vue'
import UserManagePage from '@/Pages/用户相关/UserManagePage.vue'
import ACCESS_ENUM from '@/Pages/用户相关/权限控制/accessEnum'

export const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: { title: '首页' },
  },
  {
    path: '/user/login',
    name: '用户登录',
    component: UserLoginPage,
  },
  {
    path: '/user/register',
    name: '用户注册',
    component: UserRegisterPage,
  },
  {
    path: '/admin/userManage',
    name: '用户管理',
    component: UserManagePage,
    meta: {
      title: '用户管理',
      access: ACCESS_ENUM.ADMIN,//权限控制，只有管理员才能访问用户管理页面
    },
  },
]



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
