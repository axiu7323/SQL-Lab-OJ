import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import type { CurrentUser, UserRole } from '@/types/user'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('sql_oj_token') || '')
  const user = ref<CurrentUser | null>(null)

  const role = computed<UserRole | undefined>(() => user.value?.role)
  const isLoggedIn = computed(() => Boolean(token.value))

  function setToken(nextToken: string) {
    token.value = nextToken
    localStorage.setItem('sql_oj_token', nextToken)
  }

  function setUser(nextUser: CurrentUser | null) {
    user.value = nextUser
  }

  function clearSession() {
    token.value = ''
    user.value = null
    localStorage.removeItem('sql_oj_token')
  }

  return {
    token,
    user,
    role,
    isLoggedIn,
    setToken,
    setUser,
    clearSession
  }
})
