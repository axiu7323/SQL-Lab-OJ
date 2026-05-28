# 009 联调验收报告

## 1. 验收范围

本轮执行 `docs/09-tasks/009-integration-test.md`，范围限定为静态联调检查、构建验证、测试验证、前后端 API 路径核对和缺口记录。

本轮没有启动真实 MySQL、Redis、后端服务或前端开发服务器；没有伪造数据库端到端判题成功。

## 2. 验证命令结果

| 验证项 | 命令 | 结果 |
| --- | --- | --- |
| 后端测试 | `cd backend && mvn -q test` | 通过 |
| 前端依赖安装 | `cd frontend && npm install` | 成功，0 vulnerabilities |
| 前端构建 | `cd frontend && npm run build` | 通过，有 Monaco chunk 体积警告 |

## 3. 后端模块检查

| 模块 | 主要文件 | 结论 |
| --- | --- | --- |
| 题目模块 | `ProblemController`、`ProblemApplicationService`、`ProblemRepositoryImpl`、`ProblemMapper`、`ProblemDO` | 已存在 |
| 提交模块 | `SubmissionController`、`SubmissionApplicationService`、`SubmissionRepositoryImpl`、`SubmissionMapper`、`SubmissionDO` | 已存在 |
| 判题模块 | `JudgeController`、`JudgeApplicationService`、`SqlSafetyChecker`、`ResultSetComparator`、`JudgeResultRepositoryImpl`、`JudgeResultMapper`、`JudgeResultDO` | 已存在 |

DDD 边界扫描未发现 `interfaces` 或 `application` 直接引用 Mapper，也未发现 `domain` 依赖 Spring、MyBatis、Controller、Request、Response、Entity 或 DO。

## 4. 前端模块检查

| 模块 | 主要文件 | 结论 |
| --- | --- | --- |
| 路由 | `frontend/src/router/index.ts` | 已按学生、教师、管理员拆分 |
| API | `frontend/src/api/problem.ts`、`submission.ts`、`judge.ts`、`user.ts`、`statistics.ts`、`classroom.ts` | 已按模块拆分 |
| Store | `frontend/src/store/user.ts` | 已存在基础用户状态 |
| Monaco SQL 编辑器 | `frontend/src/components/SqlEditor.vue` | 已存在 |
| 学生页面 | 题目列表、题目详情、SQL 练习、提交历史、排行榜 | 已存在 |
| 教师页面 | 题库管理、题目编辑、提交记录、统计 | 已存在 |

## 5. API 对齐结果

### problem api

已对齐：

- `GET /api/problems`
- `GET /api/problems/{problemId}`
- `GET /api/teacher/problems`
- `GET /api/teacher/problems/{problemId}`
- `POST /api/teacher/problems`
- `PUT /api/teacher/problems/{problemId}`
- `DELETE /api/teacher/problems/{problemId}`
- `POST /api/teacher/problems/{problemId}/enable`
- `POST /api/teacher/problems/{problemId}/disable`

本轮修复了前端启用/禁用题目的 HTTP 方法，将 `PUT` 修正为后端实际提供的 `POST`。

未实现：

- 文档中提到的 `POST /api/teacher/problems/{problemId}/test` 当前后端和前端均未实现。

### submission api

已对齐：

- `POST /api/submissions`
- `GET /api/submissions/my`
- `GET /api/submissions/{submissionId}`
- `GET /api/teacher/submissions`
- `GET /api/teacher/submissions/{submissionId}`

说明：当前后端尚未实现真实认证上下文，学生提交和查看详情仍通过请求参数或请求体携带 `userId`。

### judge api

已对齐：

- `POST /api/teacher/submissions/{submissionId}/judge`

### user api

前端已预留：

- `POST /api/auth/login`
- `GET /api/auth/me`
- `POST /api/auth/logout`

后端当前未实现用户认证 Controller，因此该模块不具备真实联调能力。

### statistics api

前端已预留：

- `GET /api/rankings`
- `GET /api/teacher/statistics/problems`
- `GET /api/teacher/statistics/students`

后端当前未实现排行榜和统计 Controller，因此该模块不具备真实联调能力。

### classroom api

前端已预留：

- `GET /api/admin/classes`

后端当前未实现管理端班级 Controller，因此该模块不具备真实联调能力。

## 6. 主流程验收结论

| 主流程 | 静态闭环 | 真实端到端 |
| --- | --- | --- |
| 学生查看题目列表、详情、进入 SQL 练习、提交 SQL、查看提交历史 | 具备 | 未验证 |
| 教师查看题目列表、创建题目、编辑题目 | 具备 | 未验证 |
| 教师查看提交记录、手动触发判题 | 具备 | 未验证 |

真实端到端未验证的原因：

- 当前仓库缺少 `schema.sql` 和初始化数据。
- 当前仓库缺少 `docker-compose.yml`。
- 当前没有真实 MySQL/Redis 服务编排。
- 登录认证和权限控制尚未实现。

## 7. 发现的问题

### 阻塞问题

- 缺少数据库 `schema.sql` 和初始化数据，无法完成真实数据库启动和端到端判题。
- 缺少 Docker Compose，一键部署能力尚未具备。
- 用户登录认证和权限控制尚未实现，前端登录 API 仅为预留。

### 非阻塞问题

- 统计、排行榜、管理端班级接口前端已预留，后端尚未实现。
- 文档中的题目测试接口 `POST /api/teacher/problems/{problemId}/test` 尚未实现。
- `npm run build` 存在 Monaco 相关 chunk 体积警告，不影响构建通过。

### 后续优化问题

- 前端可对 Monaco Editor 做懒加载或手动分包。
- 后端需要补充基于真实 MySQL 的集成测试。
- 沙箱数据库权限、超时和清理策略需要在部署任务中进一步加固。

## 8. 总结

当前项目已完成静态联调和构建验收：后端测试通过，前端依赖安装成功，前端构建通过，题目、提交、判题三类核心 API 基本对齐。

当前项目尚不具备真实部署给学生使用的完整能力，下一步应优先补齐数据库 schema、初始化数据、Docker Compose、认证权限和真实 MySQL 沙箱端到端验证。
