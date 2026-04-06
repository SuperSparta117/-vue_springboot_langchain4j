<script setup lang="ts"></script>

<template>
  <a-layout-footer class="footer">
    <div class="footer-content">
      原创项目 by
      <a href="https://www.codefather.cn" target="_blank" rel="noopener noreferrer">刘明皓</a>
    </div>
  </a-layout-footer>
</template>

<style scoped>
.footer {
  background: #f0f2f5;
  text-align: center;
  padding: 16px 24px;
  color: rgba(0, 0, 0, 0.45);
  font-size: 14px;
}

.footer-content a {
  color: rgba(0, 0, 0, 0.65);
  text-decoration: none;
}

.footer-content a:hover {
  color: #1890ff;
}
</style>
