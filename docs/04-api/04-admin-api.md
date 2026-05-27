# 管理端 API

## 用户管理

GET /api/admin/users，POST /api/admin/users，PUT /api/admin/users/{userId}，DELETE /api/admin/users/{userId}。

用户字段：username、password、realName、role、classId、status。

## 班级管理

GET /api/admin/classes，POST /api/admin/classes，PUT /api/admin/classes/{classId}，DELETE /api/admin/classes/{classId}。

班级字段：className、teacherId、status。
