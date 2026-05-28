<template>
  <main class="login-page">
    <section class="login-panel">
      <h1>SQL Lab OJ</h1>
      <p>学生 SQL 练习与自动判题平台</p>
      <el-form :model="form" label-position="top" @submit.prevent>
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-button type="primary" class="login-button" :loading="loading" @click="handleLogin">
          登录
        </el-button>
      </el-form>
    </section>
  </main>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/user'
import { useUserStore } from '@/store/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const form = reactive({
  username: '',
  password: ''
})

async function handleLogin() {
  loading.value = true
  try {
    const result = await login(form)
    userStore.setToken(result.token)
    userStore.setUser({
      id: result.userId,
      username: result.username,
      realName: result.realName,
      role: result.role
    })
    const target = result.role === 'TEACHER' ? '/teacher/problems' : result.role === 'ADMIN' ? '/admin/users' : '/student/problems'
    await router.push(target)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  display: grid;
  min-height: 100vh;
  place-items: center;
  padding: 24px;
  background: #edf2f7;
}

.login-panel {
  width: min(420px, 100%);
  padding: 28px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
}

.login-panel h1 {
  margin: 0 0 8px;
}

.login-panel p {
  margin: 0 0 24px;
  color: #64748b;
}

.login-button {
  width: 100%;
}
</style>
