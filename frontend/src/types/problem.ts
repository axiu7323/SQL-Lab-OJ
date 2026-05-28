import type { ProblemDifficulty, ProblemStatus } from './common'

export interface ProblemQuery {
  pageNo?: number
  pageSize?: number
  keyword?: string
  categoryId?: number
  difficulty?: ProblemDifficulty
  status?: ProblemStatus
  passed?: boolean
}

export interface ProblemItem {
  id: number
  title: string
  categoryId?: number
  categoryName: string
  difficulty: ProblemDifficulty
  status?: ProblemStatus
  score: number
  passRate?: number
  myStatus?: string
  timeLimitMs?: number
  maxRows?: number
}

export interface ProblemCase {
  id?: number
  name: string
  schemaSql: string
  initDataSql: string
  expectedSql?: string
  sampleCase?: boolean
}

export interface ProblemDetail extends ProblemItem {
  description: string
  orderSensitive: boolean
  checkColumnName: boolean
  timeLimitMs: number
  maxRows: number
  cases: ProblemCase[]
}

export interface ProblemSaveRequest {
  title: string
  description: string
  categoryId: number
  categoryName?: string
  difficulty: ProblemDifficulty
  score: number
  initSchemaSql: string
  initDataSql: string
  standardSql: string
  orderSensitive: boolean
  checkColumnName: boolean
  timeLimitMs: number
  maxRows: number
  status: ProblemStatus
}
