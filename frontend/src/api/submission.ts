import request from '@/utils/request'
import type { PageResponse } from '@/types/common'
import type {
  SubmissionDetail,
  SubmissionItem,
  SubmissionQuery,
  SubmitSqlRequest
} from '@/types/submission'

export function submitSql(data: SubmitSqlRequest) {
  return request.post<SubmissionDetail, SubmissionDetail>('/submissions', data)
}

export function getMySubmissions(params: SubmissionQuery) {
  return request.get<PageResponse<SubmissionItem>, PageResponse<SubmissionItem>>('/submissions/my', { params })
}

export function getMySubmissionDetail(submissionId: number, userId: number) {
  return request.get<SubmissionDetail, SubmissionDetail>(`/submissions/${submissionId}`, { params: { userId } })
}

export function getTeacherSubmissions(params: SubmissionQuery) {
  return request.get<PageResponse<SubmissionItem>, PageResponse<SubmissionItem>>('/teacher/submissions', { params })
}

export function getTeacherSubmissionDetail(submissionId: number) {
  return request.get<SubmissionDetail, SubmissionDetail>(`/teacher/submissions/${submissionId}`)
}
