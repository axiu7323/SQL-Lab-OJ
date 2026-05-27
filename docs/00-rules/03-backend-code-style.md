# 后端代码规范

## 1. 命名规范

| 类型 | 命名 | 示例 |
|---|---|---|
| Controller | XxxController | ProblemController |
| Request | XxxRequest | SubmitSqlRequest |
| Response | XxxResponse | SubmitSqlResponse |
| Assembler | XxxAssembler | ProblemAssembler |
| ApplicationService | XxxApplicationService | SubmitSqlApplicationService |
| Command | XxxCommand | SubmitSqlCommand |
| Query | XxxQuery | ProblemPageQuery |
| DTO | XxxDTO | ProblemDetailDTO |
| DomainService | XxxDomainService | JudgeDomainService |
| Repository 接口 | XxxRepository | ProblemRepository |
| Repository 实现 | XxxRepositoryImpl | ProblemRepositoryImpl |
| 数据库对象 | XxxDO | ProblemDO |
| Mapper | XxxMapper | ProblemMapper |
| Converter | XxxConverter | ProblemConverter |

## 2. Controller 规范

Controller 只做：接收参数、调用 ApplicationService、返回 ApiResponse。不得写判题逻辑，不得调用 Mapper。

## 3. ApplicationService 规范

ApplicationService 负责用例编排。事务边界优先放在应用层。不得直接访问 MyBatis Mapper。

## 4. Domain 规范

领域对象必须包含业务方法。不要只有 getter / setter。

示例：

```java
submission.markJudging();
submission.applyJudgeResult(result);
problem.validateForPublish();
```

## 5. Repository 规范

领域层定义接口，基础设施层实现接口。

```java
public interface ProblemRepository {
    Optional<Problem> findById(ProblemId id);
    void save(Problem problem);
}
```

## 6. 异常规范

业务异常使用 BusinessException + ErrorCode。禁止直接 `throw new RuntimeException("xxx")`。

常用错误码：USER_NOT_FOUND、PROBLEM_NOT_FOUND、PROBLEM_NOT_ENABLED、SQL_NOT_SAFE、JUDGE_TIMEOUT、PERMISSION_DENIED。

## 7. 日志规范

日志需要包含 userId、problemId、submissionId、judgeTaskId、status、executeTimeMs。禁止打印密码、Token、数据库密码和超长 SQL。

## 8. 测试规范

必须重点测试 SqlSafetyChecker、ResultSetComparator、ScoreCalculator、JudgeDomainService、SubmitSqlApplicationService。
