# 学生端 API

## GET /api/problems

查询题目列表。参数：pageNo、pageSize、keyword、categoryId、difficulty、passed。

返回字段：id、title、categoryName、difficulty、score、passRate、myStatus。

## GET /api/problems/{problemId}

查询题目详情。学生端不能返回 standardSql。返回题目描述、表结构说明、样例数据、分数、难度、orderSensitive、checkColumnName、timeLimitMs。

## POST /api/submissions

提交 SQL。请求体：problemId、submitSql。返回 submissionId、status、score、message、executeTimeMs、diff。

## GET /api/submissions/my

查询我的提交记录。参数：pageNo、pageSize、problemId、status。

## GET /api/submissions/{submissionId}

查询提交详情。学生只能查询自己的提交。

## GET /api/rankings

查询排行榜，返回 rank、userId、realName、passedCount、totalScore、submitCount。
