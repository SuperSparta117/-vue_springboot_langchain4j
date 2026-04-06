<script setup lang="ts">
import { h } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
import { routes } from '@/router'

const route = useRoute()

// 从路由配置生成菜单项
const menuItems = routes
  .filter((r) => r.meta?.title)
  .map((r) => ({
    key: r.path,
    label: h(RouterLink, { to: r.path }, () => r.meta?.title as string),
  }))
</script>

<template>
  <a-layout-header class="header">
    <div class="header-content">
      <div class="left-section">
        <RouterLink to="/" class="logo-link">
          <img src="@/assets/logo.svg" alt="Logo" class="logo" />
          <span class="title">VueAndLangChain4j</span>
        </RouterLink>
        <a-menu
          mode="horizontal"
          :selected-keys="[route.path]"
          :items="menuItems"
          class="header-menu"
        />
      </div>
      <div class="right-section">
        <a-button type="primary">登录</a-button>
      </div>
    </div>
  </a-layout-header>
</template>

<style scoped>
.header {
  background: #fff;
  padding: 0 24px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-width: 1400px;
  margin: 0 auto;
  height: 64px;
}

.left-section {
  display: flex;
  align-items: center;
  flex: 1;
  overflow: hidden;
}

.logo-link {
  display: flex;
  align-items: center;
  text-decoration: none;
  margin-right: 24px;
  flex-shrink: 0;
}

.logo {
  height: 32px;
  width: 32px;
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: rgba(0, 0, 0, 0.85);
  margin-left: 10px;
  white-space: nowrap;
}

.header-menu {
  flex: 1;
  border-bottom: none;
  line-height: 64px;
}

.right-section {
  flex-shrink: 0;
  margin-left: 16px;
}

@media (max-width: 768px) {
  .title {
    display: none;
  }

  .header {
    padding: 0 12px;
  }
}
</style>
