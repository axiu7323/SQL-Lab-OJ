export interface RankingItem {
  rank: number
  userId: number
  realName: string
  passedCount: number
  totalScore: number
  submitCount: number
}

export interface ProblemStatistics {
  problemId: number
  title: string
  submittedStudentCount: number
  acceptedStudentCount: number
  passRate: number
  submitCount: number
}

export interface StudentStatistics {
  studentId: number
  realName: string
  submitCount: number
  passedCount: number
  totalScore: number
  lastSubmittedAt?: string
}
