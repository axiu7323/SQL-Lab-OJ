<template>
  <el-container class="app-shell">
    <el-aside width="232px" class="app-sidebar">
      <div class="brand">
        <span class="brand-mark">SQL</span>
        <div>
          <strong>SQL Lab OJ</strong>
          <small>在线练习判题</small>
        </div>
      </div>

      <el-menu :default-active="route.path" router class="nav-menu">
        <el-menu-item index="/student/problems">学生题目</el-menu-item>
        <el-menu-item index="/student/submissions">我的提交</el-menu-item>
        <el-menu-item index="/student/ranking">排行榜</el-menu-item>
        <el-menu-item index="/teacher/problems">题库管理</el-menu-item>
        <el-menu-item index="/teacher/submissions">提交记录</el-menu-item>
        <el-menu-item index="/teacher/statistics">练习统计</el-menu-item>
        <el-menu-item index="/admin/users">用户管理</el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="app-header">
        <div>
          <strong>{{ route.meta.title || 'SQL Lab OJ' }}</strong>
          <span class="header-role">当前为前端骨架阶段</span>
        </div>
        <el-button text @click="router.push('/login')">登录入口</el-button>
      </el-header>

      <el-main class="app-main">
        <RouterView />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
</script>

<style scoped>
.app-shell {
  min-height: 100vh;
}

.app-sidebar {
  display: flex;
  flex-direction: column;
  background: #172033;
  color: #e5e7eb;
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
  height: 72px;
  padding: 0 18px;
}

.brand small {
  display: block;
  margin-top: 3px;
  color: #9ca3af;
}

.brand-mark {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 42px;
  height: 42px;
  border-radius: 8px;
  background: #22c55e;
  color: #052e16;
  font-weight: 800;
}

.nav-menu {
  flex: 1;
  border-right: 0;
  background: transparent;
}

.nav-menu :deep(.el-menu-item) {
  color: #cbd5e1;
}

.nav-menu :deep(.el-menu-item.is-active),
.nav-menu :deep(.el-menu-item:hover) {
  color: #fff;
  background: rgba(255, 255, 255, 0.08);
}

.app-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #e5e7eb;
  background: #fff;
}

.header-role {
  margin-left: 12px;
  color: #64748b;
  font-size: 13px;
}

.app-main {
  padding: 22px;
}

@media (max-width: 760px) {
  .app-sidebar {
    display: none;
  }

  .app-main {
    padding: 14px;
  }
}
</style>
