# 用户模块设计

## 1. 模块职责

用户模块负责用户身份、角色、登录、权限和班级归属。

## 2. 核心对象

- User
- UserId
- Username
- PasswordHash
- UserRole
- UserStatus

## 3. 核心功能

- 登录
- 查询当前用户
- 管理员维护用户
- 教师查看班级学生

## 4. 领域规则

- 所有领域对象必须包含业务方法，不允许只有 getter / setter。
- 模块内部规则放在 domain 层。
- 跨模块流程由 application 层编排。
- 数据库访问必须通过 Repository 接口。
- Controller 不能直接访问 Mapper。

## 5. 接口或服务

- `POST /api/auth/login`
- `GET /api/auth/me`
- `POST /api/auth/logout`
- `GET /api/admin/users`
- `POST /api/admin/users`

## 6. 数据持久化建议

- Repository 接口放在 domain 层。
- RepositoryImpl 放在 infrastructure 层。
- DO 和 Mapper 放在 infrastructure/persistence 下。
- Converter 负责 DO 和 Domain 转换。

## 7. 验收标准

- 模块职责单一。
- 接口字段和 API 文档一致。
- 权限校验正确。
- 异常统一使用 BusinessException。
- 能通过本模块核心用例测试。
