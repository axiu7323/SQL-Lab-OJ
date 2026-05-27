# 判题引擎设计

流程：创建 JudgeTask，SQL 安全检查，创建沙箱，执行初始化建表 SQL，执行初始化数据 SQL，执行标准 SQL，执行学生 SQL，比较结果集，计算分数，清理沙箱，返回 JudgeResult。

状态：AC 正确，WA 答案错误，RE 运行异常，TLE 超时，CE SQL 不合法或安全失败，SE 系统错误。

接口建议：JudgeEngine、JudgeDomainService、SqlSafetyChecker、ResultSetComparator、ScoreCalculator、SqlSandbox。
