import request from '@/utils/request'
import type { CurrentUser, LoginRequest, LoginResult } from '@/types/user'

export function login(data: LoginRequest) {
  return request.post<LoginResult, LoginResult>('/auth/login', data)
}

export function getCurrentUser() {
  return request.get<CurrentUser, CurrentUser>('/auth/me')
}

export function logout() {
  return request.post<void, void>('/auth/logout')
}
