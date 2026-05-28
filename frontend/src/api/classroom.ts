import request from '@/utils/request'
import type { PageResponse } from '@/types/common'
import type { ClassInfo } from '@/types/classroom'

export function getClasses(params?: { pageNo?: number; pageSize?: number }) {
  return request.get<PageResponse<ClassInfo>, PageResponse<ClassInfo>>('/admin/classes', { params })
}
