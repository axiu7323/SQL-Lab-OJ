# 项目通用规则

## 1. 项目目标

本项目是 SQL 在线练习 OJ 判题系统，核心目标是让学生在线练习 SQL，让系统自动判题、评分、记录历史，让教师可以维护题库并分析学生练习效果。

## 2. 架构约束

系统必须遵守：

- 前后端分离。
- 后端采用 Spring Boot 3 + Java 17。
- 后端采用模块化单体，不做微服务。
- 后端采用 DDD 分层：interfaces、application、domain、infrastructure、common。
- 前端采用 Vue 3 + Vite + TypeScript + Element Plus + Monaco Editor。
- 判题模块必须独立抽象。
- 数据库、接口、领域模型必须先有文档约束，再改代码。

## 3. 禁止行为

Codex 或开发者不得：

- Controller 直接调用 Mapper。
- Controller 直接写判题逻辑。
- ApplicationService 直接调用 MyBatis Mapper。
- Domain 层依赖 Spring、MyBatis、数据库 Entity。
- 把判题逻辑堆到普通 ServiceImpl。
- 把所有前端 API 写到一个文件。
- 把所有页面逻辑写到一个 Vue 文件。
- 跳过 docs 文档直接大范围改代码。
- 引入未经确认的新技术栈。

## 4. V1 范围

V1 只做 MySQL SQL 判题，且只支持 SELECT / WITH 查询。暂不做 Hive、Spark SQL、Doris、ClickHouse、AI 出题、复杂多租户和微服务。

## 5. 用户角色

### 学生

允许：看题、写 SQL、提交、查看自己的提交、查看排行榜。  
禁止：改题、删题、查看他人完整 SQL、管理用户。

### 教师

允许：维护题目、查看学生提交、查看统计。  
禁止：修改系统级配置、管理管理员账号、绕过判题规则。

### 管理员

允许：管理用户、角色、班级和系统配置。

## 6. 安全原则

- 学生 SQL 必须经过安全检查。
- 学生 SQL 必须在隔离环境中执行。
- 必须设置执行超时。
- 必须限制最大返回行数。
- 判题结束必须清理临时库。
- 学生不能直接接触标准答案 SQL。
