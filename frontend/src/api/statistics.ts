import request from '@/utils/request'
import type { RankingItem, ProblemStatistics, StudentStatistics } from '@/types/statistics'

export function getRankings() {
  return request.get<RankingItem[], RankingItem[]>('/rankings')
}

export function getProblemStatistics() {
  return request.get<ProblemStatistics[], ProblemStatistics[]>('/teacher/statistics/problems')
}

export function getStudentStatistics() {
  return request.get<StudentStatistics[], StudentStatistics[]>('/teacher/statistics/students')
}
