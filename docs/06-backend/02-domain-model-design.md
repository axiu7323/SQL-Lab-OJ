# 领域模型设计

User 聚合：User、UserId、Username、PasswordHash、UserRole、UserStatus。  
Problem 聚合：Problem、ProblemId、ProblemCategory、Difficulty、ProblemStatus、JudgeConfig、SqlScript。  
Submission 聚合：Submission、SubmissionId、SubmitSql、SubmissionStatus、JudgeScore。  
Judge 领域：JudgeTask、JudgeResult、JudgeStatus、SqlExecutionResult、ResultSetDiff。  
Sandbox 领域：SqlSandbox、SandboxContext、SandboxExecuteResult。

领域对象必须包含业务方法。例如 Problem.canSubmit()、Problem.validateForPublish()、Submission.markJudging()、Submission.applyJudgeResult()。
