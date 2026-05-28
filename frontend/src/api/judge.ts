import request from '@/utils/request'
import type { JudgeResult } from '@/types/judge'

export function judgeSubmission(submissionId: number) {
  return request.post<JudgeResult, JudgeResult>(`/teacher/submissions/${submissionId}/judge`)
}
