# 沙箱设计

V1 使用 MySQL 临时库。每次判题创建 judge_${submissionId}_${timestamp}，执行初始化 SQL、标准 SQL、学生 SQL，最后 drop database。

必须 try-finally 清理。清理失败要记录日志，并后续定时清理 judge_ 前缀数据库。

V2/V3 可以升级为独立 MySQL 容器池或多 SQL 引擎沙箱。
