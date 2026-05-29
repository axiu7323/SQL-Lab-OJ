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

- 暂未完成登录认证、权限控制和真实数据库端到端联调。

## 数据库初始化

数据库脚本位于 `sql` 目录：

- `sql/schema.sql`：创建 MySQL 8 数据库和表结构。
- `sql/data.sql`：写入演示用户、班级、题目和提交数据。

导入命令：

```bash
mysql -uroot -p < sql/schema.sql
mysql -uroot -p < sql/data.sql
```

更详细说明见 `sql/README.md`。

## Docker Compose 一键部署

当前仓库已提供根目录 `docker-compose.yml`，包含 MySQL 8、Redis 7、后端 Spring Boot 服务和前端 Nginx 服务。

### 启动前检查

```bash
docker --version
docker compose version
docker compose config
```

### 启动服务

```bash
docker compose up -d --build
```

首次启动时，MySQL 会自动执行：

- `sql/schema.sql`
- `sql/data.sql`

### 访问地址

- 前端页面：http://localhost:8081
- 后端接口：http://localhost:8080/api/health
- MySQL：`localhost:3306`
- Redis：`localhost:6379`

### 默认环境变量

```text
MYSQL_ROOT_PASSWORD=root_password
MYSQL_DATABASE=sql_lab_oj
MYSQL_USER=sqloj
MYSQL_PASSWORD=sqloj_password
BACKEND_PORT=8080
FRONTEND_PORT=8081
```

后端容器通过以下环境变量连接依赖服务：

- `SQL_OJ_DB_URL`
- `SQL_OJ_DB_USERNAME`
- `SQL_OJ_DB_PASSWORD`
- `SQL_OJ_REDIS_HOST`
- `SQL_OJ_REDIS_PORT`

### 停止服务

```bash
docker compose down
```

如需同时删除 MySQL 和 Redis 数据卷：

```bash
docker compose down -v
```

注意：当前项目尚未实现登录认证和完整权限控制，Docker 部署主要用于本地演示和后续端到端联调。

## 推荐 Codex 提示词

```text
请严格按照 docs/09-tasks/001-review-current-project.md 执行任务。
执行前必须阅读任务文件中列出的所有规范文件。
本轮只允许执行当前任务，不允许修改任务范围之外的文件。
修改前先输出执行计划，修改后输出变更文件清单、验收结果和未完成事项。
```
