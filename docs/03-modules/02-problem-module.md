# 题库模块设计

## 1. 模块职责

题库模块负责SQL 题目的创建、编辑、查询、状态管理和判题配置。

## 2. 核心对象

- Problem
- ProblemId
- ProblemCategory
- Difficulty
- ProblemStatus
- JudgeConfig
- SqlScript

## 3. 核心功能

- 题目列表
- 题目详情
- 新增题目
- 编辑题目
- 禁用题目
- 测试标准答案

## 4. 领域规则

- 所有领域对象必须包含业务方法，不允许只有 getter / setter。
- 模块内部规则放在 domain 层。
- 跨模块流程由 application 层编排。
- 数据库访问必须通过 Repository 接口。
- Controller 不能直接访问 Mapper。

## 5. 接口或服务

- `GET /api/problems`
- `GET /api/problems/{problemId}`
- `POST /api/teacher/problems`
- `PUT /api/teacher/problems/{problemId}`
- `DELETE /api/teacher/problems/{problemId}`

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
