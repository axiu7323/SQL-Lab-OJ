# 部署架构设计

## 1. V1 部署目标

支持 Docker Compose 本地一键启动，适合开发、演示和局域网教学。

## 2. 服务

```text
mysql:8.0
redis:7
backend
frontend-nginx
```

## 3. 端口

frontend: 80，backend: 8080，mysql: 3306，redis: 6379。

## 4. 初始化

MySQL 启动执行 schema.sql 和 init-data.sql。

## 5. 判题临时库

临时库命名：`judge_${submissionId}_${timestamp}`。判题完成必须删除。

## 6. 演进

V2 引入异步判题 Worker，V3 引入多 SQL 引擎和独立沙箱容器池。
