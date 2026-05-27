# 总体架构设计

## 1. 架构目标

SQL OJ 判题系统采用前后端分离、后端 DDD 模块化单体、前端模块化、判题引擎抽象的架构。第一版优先稳定实现 MySQL SQL 判题，后续可扩展到 Hive、Spark SQL、Doris 和 ClickHouse。

## 2. 总体链路

```text
学生 / 教师 / 管理员
        |
        v
Vue 3 前端
        |
        | REST API
        v
Spring Boot 后端
        |
        +-- 用户与权限
        +-- 题库管理
        +-- SQL 提交
        +-- 判题引擎
        +-- 沙箱执行
        +-- 统计分析
        |
        v
MySQL / Redis / Docker Sandbox
```

## 3. 前端职责

页面展示、用户交互、SQL 编辑器、调用 API、展示判题结果、角色菜单和路由控制。

## 4. 后端职责

认证鉴权、题库维护、提交记录、判题调度、SQL 安全检查、沙箱执行、结果集比较、统计分析。

## 5. 限界上下文

User、Problem、Submission、Judge、Sandbox、Classroom、Statistics、Shared。

## 6. 核心流程：学生提交 SQL

1. 学生打开题目详情。
2. 前端展示题目描述、表结构、样例数据。
3. 学生编写 SQL 并提交。
4. 后端创建提交记录。
5. 后端执行 SQL 安全检查。
6. 后端创建临时数据库并初始化题目数据。
7. 后端执行标准 SQL 和学生 SQL。
8. 后端比较结果集。
9. 后端保存判题结果和提交状态。
10. 前端展示状态、得分、错误信息和 diff。

## 7. 部署架构

V1 使用 Docker Compose：mysql、redis、backend、frontend-nginx。判题临时库先放在同一个 MySQL 实例中，后续可演进为独立沙箱容器。
