# SQL OJ 判题系统

本项目是面向学生 SQL 练习的在线 OJ 判题系统，当前已经包含文档体系、Spring Boot DDD 后端骨架、题目模块、提交模块、判题模块，以及 Vue 3 + Vite + TypeScript 前端基础结构。

## 推荐使用方式

1. 先读 `PROJECT_SPEC.md`。
2. 再读 `docs/00-rules`，这是长期约束。
3. 再读 `docs/01-architecture`，这是目标架构。
4. 再读 `docs/03-modules`，这是各模块需求。
5. 再读 `docs/04-api` 和 `docs/05-database`，这是接口和数据契约。
6. 最后执行 `docs/09-tasks` 中的任务文件。
7. 每个任务完成后用 `docs/10-review` 清单验收。
8. 验收通过后 Git 提交。

## 本地启动与验证

### 后端

当前后端工程位于 `backend` 目录，使用 Java 17、Spring Boot 3、Maven、MyBatis-Plus、MySQL 8 和 Redis。

```bash
cd backend
mvn -q test
```

如需启动后端服务，需要先准备 MySQL 和 Redis，并配置 `application-local.yml` 中的数据源连接。当前仓库尚未补齐 `schema.sql`，因此真实数据库启动和端到端判题仍需要后续数据库初始化任务支持。

### 前端

当前前端工程位于 `frontend` 目录，使用 Vue 3、Vite、TypeScript、Element Plus、Pinia、Axios、Vue Router 和 Monaco Editor。

```bash
cd frontend
npm install
npm run build
```

本地开发可使用：

```bash
cd frontend
npm run dev
```

前端开发服务器会将 `/api` 请求代理到 `http://localhost:8080`。

### 当前缺口

- 暂未提供 `schema.sql` 或数据库初始化数据。
- 暂未提供 `docker-compose.yml`。
- 暂未完成登录认证、权限控制和真实数据库端到端联调。

## 推荐 Codex 提示词

```text
请严格按照 docs/09-tasks/001-review-current-project.md 执行任务。
执行前必须阅读任务文件中列出的所有规范文件。
本轮只允许执行当前任务，不允许修改任务范围之外的文件。
修改前先输出执行计划，修改后输出变更文件清单、验收结果和未完成事项。
```
