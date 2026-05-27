# 安全规范

## 1. SQL 执行安全

V1 只允许 SELECT 和 WITH。必须禁止 INSERT、UPDATE、DELETE、DROP、ALTER、TRUNCATE、CREATE、REPLACE、GRANT、REVOKE、CALL、EXEC、LOAD_FILE、INTO OUTFILE、INTO DUMPFILE、SLEEP、BENCHMARK。

## 2. 多语句禁止

禁止 `select * from users; drop table users;` 这类多语句。可以允许末尾单个分号。

## 3. 超时和返回行数

默认超时 3000ms，最大不超过 10000ms。默认最大返回 1000 行。

## 4. 沙箱隔离

每次判题必须创建独立临时库：`judge_${submissionId}_${timestamp}`。执行完成必须清理。

## 5. 权限

学生只能访问自己的提交记录。教师只能访问教师接口和自己负责范围的数据。管理员访问管理接口。

## 6. 日志脱敏

禁止记录密码、Token、数据库密码和完整超长 SQL。SQL 日志最多记录前 500 字符。
