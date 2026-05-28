import type { JudgeStatus } from './common'

export interface SubmitSqlRequest {
  userId?: number
  problemId: number
  submitSql: string
}

export interface SubmissionQuery {
  pageNo?: number
  pageSize?: number
  userId?: number
  studentId?: number
  problemId?: number
  status?: JudgeStatus
  startTime?: string
  endTime?: string
}

export interface SubmissionItem {
  id: number
  userId: number
  problemId: number
  status: JudgeStatus
  score: number
  message: string
  executeTimeMs: number
  submittedAt: string
  judgedAt?: string
}

export interface SubmissionDetail extends SubmissionItem {
  submitSql: string
}
