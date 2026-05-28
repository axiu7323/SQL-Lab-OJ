import request from '@/utils/request'
import type { PageResponse } from '@/types/common'
import type { ProblemDetail, ProblemItem, ProblemQuery, ProblemSaveRequest } from '@/types/problem'

export function getStudentProblems(params: ProblemQuery) {
  return request.get<PageResponse<ProblemItem>, PageResponse<ProblemItem>>('/problems', { params })
}

export function getStudentProblemDetail(problemId: number) {
  return request.get<ProblemDetail, ProblemDetail>(`/problems/${problemId}`)
}

export function getTeacherProblems(params: ProblemQuery) {
  return request.get<PageResponse<ProblemItem>, PageResponse<ProblemItem>>('/teacher/problems', { params })
}

export function getTeacherProblemDetail(problemId: number) {
  return request.get<ProblemDetail, ProblemDetail>(`/teacher/problems/${problemId}`)
}

export function createProblem(data: ProblemSaveRequest) {
  return request.post<ProblemDetail, ProblemDetail>('/teacher/problems', data)
}

export function updateProblem(problemId: number, data: ProblemSaveRequest) {
  return request.put<ProblemDetail, ProblemDetail>(`/teacher/problems/${problemId}`, data)
}

export function deleteProblem(problemId: number) {
  return request.delete<void, void>(`/teacher/problems/${problemId}`)
}

export function enableProblem(problemId: number) {
  return request.post<void, void>(`/teacher/problems/${problemId}/enable`)
}

export function disableProblem(problemId: number) {
  return request.post<void, void>(`/teacher/problems/${problemId}/disable`)
}
