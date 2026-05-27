# SQL OJ 判题系统项目总说明

## 1. 项目定位

SQL OJ 判题系统面向大数据培训、SQL 教学、SQL 面试训练和数据仓库实战训练场景。学生可以在线查看题目、编写 SQL、提交 SQL、获得自动判题结果和分数；教师可以维护题库、查看学生练习情况、分析题目通过率和班级学习效果。

本项目不是普通 CRUD 后台，而是一个包含判题引擎、SQL 安全、沙箱执行、结果集比较、评分统计和教学管理的领域型系统。

## 2. 技术栈

后端：Java 17、Spring Boot 3、Maven、MyBatis-Plus、MySQL 8.0、Redis、JUnit 5。  
前端：Vue 3、Vite、TypeScript、Element Plus、Monaco Editor、Pinia、Axios、Vue Router。  
部署：Docker Compose、Nginx、MySQL、Redis。  
判题：V1 只支持 MySQL SELECT / WITH 查询，后续扩展 Hive SQL、Spark SQL、Doris SQL、ClickHouse SQL。

## 3. 架构原则

- 前后端分离。
- 后端采用模块化单体，不直接做微服务。
- 后端遵守 DDD 分层：interfaces、application、domain、infrastructure、common。
- 领域层不依赖 Spring、MyBatis、数据库 Entity、Controller、Request、Response。
- 判题引擎独立抽象，不能和 Controller、Mapper、普通 CRUD Service 强耦合。
- 前端按角色、页面、组件、API、类型定义拆分。
- 所有后续 Codex 任务必须以 docs 文档为依据。

## 4. V1 最小可用目标

- 学生登录。
- 学生查看题目列表和题目详情。
- 学生在 SQL 编辑器中编写 SQL。
- 学生提交 SQL 后自动判题。
- 系统返回 AC、WA、RE、TLE、CE、SE。
- 学生查看自己的提交历史。
- 教师新增、编辑、禁用、删除题目。
- 教师查看学生提交记录和题目通过率。
- Docker Compose 本地启动。

## 5. 后续版本

V2：班级管理、作业发布、错题本、章节化题库。  
V3：Hive / Spark SQL / Doris / ClickHouse 判题和数仓实战题库。  
V4：AI 自动出题、AI 解释错误 SQL、AI 生成提示和相似题。
