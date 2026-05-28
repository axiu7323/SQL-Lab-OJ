import axios, { type AxiosError, type AxiosResponse } from 'axios'
import { ElMessage } from 'element-plus'
import type { ApiResponse } from '@/types/common'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('sql_oj_token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

request.interceptors.response.use(
  <T>(response: AxiosResponse<ApiResponse<T>>) => {
    const body = response.data
    if (body.code && body.code !== '00000') {
      ElMessage.error(body.message || '请求失败')
      return Promise.reject(new Error(body.message || 'request failed'))
    }
    return body.data
  },
  (error: AxiosError<ApiResponse<unknown>>) => {
    const status = error.response?.status
    const message = error.response?.data?.message || error.message || '网络请求异常'
    if (status === 401) {
      ElMessage.error('登录已失效，请重新登录')
    } else if (status === 403) {
      ElMessage.error('没有权限执行该操作')
    } else if (status && status >= 500) {
      ElMessage.error('服务暂时不可用')
    } else {
      ElMessage.error(message)
    }
    return Promise.reject(error)
  }
)

export default request
