# SQL 初始化说明

本目录提供 SQL Lab OJ 的 MySQL 8 初始化脚本。

## 文件说明

- `schema.sql`：创建 `sql_lab_oj` 数据库和 V1 表结构。
- `data.sql`：写入演示用户、班级、题目、提交记录和统计数据。

## 导入方式

在本机已安装 MySQL 8 的情况下执行：

```bash
mysql -uroot -p < sql/schema.sql
mysql -uroot -p < sql/data.sql
```

如果已经手动创建数据库，也可以进入 MySQL 后执行：

```sql
SOURCE sql/schema.sql;
SOURCE sql/data.sql;
```

## 演示数据

- 教师用户：`teacher_demo`
- 学生用户：`student_demo`
- 演示班级：`SQL Basic Class 01`
- 演示题目：`Query all student names`
- 标准答案：`SELECT name FROM student;`

当前项目尚未实现登录认证，`password_hash` 使用占位值 `demo-password-hash`。后续认证任务需要替换为真实密码哈希。

## 注意事项

- 本脚本仅覆盖当前后端已经实现的题目、提交、判题结果相关表，并预留用户、角色、班级和统计表。
- `oj_judge_result` 中 `problem_id`、`user_id`、`expected_result`、`actual_result`、`diff_type`、`diff_message` 是后续统计和诊断预留字段，当前 `JudgeResultDO` 尚未写入这些字段。
- 当前仓库尚未提供 Docker Compose，一键初始化会在后续部署任务中补齐。
