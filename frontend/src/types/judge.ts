import type { JudgeStatus } from './common'

export interface JudgeResult {
  submissionId: number
  status: JudgeStatus
  score: number
  message: string
  detail: string
  executeTimeMs: number
  judgedAt: string
}
