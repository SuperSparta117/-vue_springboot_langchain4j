<script setup lang="ts">
import BasicLayout from '@/layouts/BasicLayout.vue'

// 一般我们会尝试获取登录用户信息，在首次进入到页面时，编写远程获取数据代码:
import { useLoginUserStore } from '@/stores/loginUser.ts'
const loginUserStore = useLoginUserStore()
loginUserStore.fetchLoginUser()

</script>

<template>
  <BasicLayout />
</template>

<style>
body {
  margin: 0;
  padding: 0;
}
</style>
