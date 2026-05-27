# API 总览

## 1. 统一响应

所有接口返回 ApiResponse。分页接口 data 中返回 PageResponse。

## 2. 认证接口

POST /api/auth/login，GET /api/auth/me，POST /api/auth/logout。

## 3. 学生接口

GET /api/problems，GET /api/problems/{problemId}，POST /api/submissions，GET /api/submissions/my，GET /api/rankings。

## 4. 教师接口

GET /api/teacher/problems，POST /api/teacher/problems，PUT /api/teacher/problems/{problemId}，DELETE /api/teacher/problems/{problemId}，GET /api/teacher/submissions，GET /api/teacher/statistics/problems，GET /api/teacher/statistics/students。

## 5. 管理接口

GET /api/admin/users，POST /api/admin/users，PUT /api/admin/users/{userId}，DELETE /api/admin/users/{userId}，GET /api/admin/classes，POST /api/admin/classes。
