# DDD 分层规则

## 1. 分层目标

后端采用 DDD 分层，目标是让业务规则和技术实现解耦，让 SQL 判题、题库、提交、统计等领域边界清晰。

## 2. 分层结构

```text
common
interfaces
application
domain
infrastructure
```

## 3. interfaces 接口层

职责：接收 HTTP 请求、参数校验、调用应用服务、返回响应。

允许：Controller、Request、Response、Assembler。  
禁止：访问 Mapper、访问 RepositoryImpl、写 SQL 安全规则、写结果集比较逻辑、写复杂业务流程。

## 4. application 应用层

职责：编排完整业务用例。

例如学生提交 SQL：

1. 接收 SubmitSqlCommand。
2. 查询用户。
3. 查询题目。
4. 创建提交记录。
5. 调用判题领域服务。
6. 更新提交结果。
7. 返回 DTO。

允许：调用 Repository 接口、调用领域服务、控制事务、组装 DTO。  
禁止：直接调用 Mapper、直接依赖数据库 DO、直接处理 HTTP Request / Response、直接写底层 JDBC 执行逻辑。

## 5. domain 领域层

职责：承载核心业务规则。

包括：实体、值对象、聚合根、领域服务、Repository 接口、领域事件。

允许：Problem、Submission、JudgeTask、JudgeResult、SqlSafetyChecker、ResultSetComparator、ScoreCalculator。  
禁止：Spring 注解、MyBatis 注解、Mapper、DO、Controller、RedisTemplate、JdbcTemplate 具体实现。

## 6. infrastructure 基础设施层

职责：实现技术细节。

包括：MySQL、MyBatis-Plus、Redis、JWT、SQL 执行器、Docker 沙箱、Repository 实现、DO 与 Domain 转换器。

## 7. common 公共层

职责：放通用能力。

包括：ApiResponse、PageResponse、BusinessException、ErrorCode、GlobalExceptionHandler、通用工具类。

## 8. 正确依赖方向

```text
interfaces -> application
application -> domain
infrastructure -> domain
infrastructure -> common
bootstrap -> all
```

错误依赖：

```text
domain -> infrastructure
domain -> application
application -> mapper
controller -> mapper
```

## 9. 贫血模型判断

如果领域对象只有 getter / setter，没有业务方法，就是贫血模型。Submission 应该有 markJudging、markAccepted、markWrongAnswer 等方法；Problem 应该有 canSubmit、enable、disable、validateForPublish 等方法。
