# 后端 DDD 架构设计

## 1. 目标目录

```text
src/main/java/com/example/sqloj
├── common
├── interfaces
├── application
├── domain
└── infrastructure
```

## 2. interfaces

包含 controller、request、response、assembler。只处理 HTTP 协议和参数转换。

## 3. application

包含 command、query、dto、service。负责用例编排和事务边界。

## 4. domain

包含 user、problem、submission、judge、sandbox、classroom、statistics、shared。负责业务对象、业务规则、领域服务和 Repository 接口。

## 5. infrastructure

包含 persistence/entity、persistence/mapper、persistence/converter、repository、sandbox、redis、security、config。负责技术实现。

## 6. 调用链示例

```text
SubmissionController
  -> SubmitSqlApplicationService
    -> UserRepository
    -> ProblemRepository
    -> SubmissionRepository
    -> JudgeDomainService
       -> SqlSafetyChecker
       -> SqlSandbox
       -> ResultSetComparator
       -> ScoreCalculator
    -> JudgeResultRepository
```

## 7. 事务建议

V1 同步判题可以先放在应用服务主流程中，但建议拆成两个事务：创建提交记录、更新判题结果。V2 改为异步队列。
