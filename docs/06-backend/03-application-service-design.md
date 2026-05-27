# 应用服务设计

AuthApplicationService：登录、退出、查询当前用户。  
ProblemQueryApplicationService：题目列表、题目详情。  
TeacherProblemApplicationService：新增、编辑、删除、禁用、测试题目。  
SubmitSqlApplicationService：提交 SQL 主流程。  
SubmissionQueryApplicationService：我的提交、提交详情、教师提交查询。  
StatisticsApplicationService：排行榜、题目统计、学生统计。

SubmitSqlApplicationService 流程：接收 Command，查用户，查题目，校验可提交，创建 Submission，调用 JudgeDomainService，应用 JudgeResult，保存记录，返回 DTO。
