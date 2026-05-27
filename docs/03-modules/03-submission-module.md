# 提交模块设计

## 1. 模块职责

提交模块负责学生提交 SQL、提交记录、状态流转、得分和错误信息。

## 2. 核心对象

- Submission
- SubmissionId
- SubmitSql
- SubmissionStatus
- JudgeScore

## 3. 核心功能

- 提交 SQL
- 更新判题状态
- 查询我的提交
- 教师查询提交

## 4. 领域规则

- 所有领域对象必须包含业务方法，不允许只有 getter / setter。
- 模块内部规则放在 domain 层。
- 跨模块流程由 application 层编排。
- 数据库访问必须通过 Repository 接口。
- Controller 不能直接访问 Mapper。

## 5. 接口或服务

- `POST /api/submissions`
- `GET /api/submissions/my`
- `GET /api/submissions/{submissionId}`
- `GET /api/teacher/submissions`

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
