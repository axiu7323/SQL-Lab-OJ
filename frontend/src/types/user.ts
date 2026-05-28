export type UserRole = 'STUDENT' | 'TEACHER' | 'ADMIN'

export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResult {
  token: string
  userId: number
  username: string
  realName: string
  role: UserRole
}

export interface CurrentUser {
  id: number
  username: string
  realName: string
  role: UserRole
}
