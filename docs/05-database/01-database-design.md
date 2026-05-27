# 数据库设计总览

## 1. 设计原则

数据库负责持久化，领域模型负责业务行为。表设计要支持用户、题目、提交、判题结果、班级和统计。

## 2. 表清单

sys_user、oj_classroom、oj_class_student、oj_problem_category、oj_problem、oj_submission、oj_judge_result、oj_student_statistics、oj_problem_statistics。

## 3. 关系

sys_user 1 对多 oj_submission；oj_problem 1 对多 oj_submission；oj_submission 1 对 1 oj_judge_result；oj_problem_category 1 对多 oj_problem；oj_classroom 1 对多 sys_user。

## 4. 命名

表名小写下划线，字段小写下划线，主键 id，时间字段 created_at / updated_at，逻辑删除 deleted。
