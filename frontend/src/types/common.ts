export interface ApiResponse<T> {
  code: string
  message: string
  data: T
}

export interface PageResponse<T> {
  records: T[]
  total: number
  pageNo: number
  pageSize: number
}

export type JudgeStatus = 'PENDING' | 'JUDGING' | 'AC' | 'WA' | 'RE' | 'TLE' | 'CE' | 'SE'

export type ProblemDifficulty = 'EASY' | 'MEDIUM' | 'HARD'

export type ProblemStatus = 'DRAFT' | 'ENABLED' | 'DISABLED'
