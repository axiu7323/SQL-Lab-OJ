# 教师端 API

## GET /api/teacher/problems

教师题目列表，支持关键词、分类、难度、状态筛选。

## POST /api/teacher/problems

新增题目。字段：title、description、categoryId、difficulty、score、initSchemaSql、initDataSql、standardSql、orderSensitive、checkColumnName、timeLimitMs、maxRows、status。

## PUT /api/teacher/problems/{problemId}

编辑题目。字段同新增。

## DELETE /api/teacher/problems/{problemId}

逻辑删除题目。

## POST /api/teacher/problems/{problemId}/test

测试题目初始化 SQL 和标准答案 SQL 是否可执行。

## GET /api/teacher/submissions

查询提交记录。参数：studentId、problemId、status、classId、startTime、endTime。

## GET /api/teacher/statistics/problems

题目统计：提交人数、通过人数、通过率、提交次数。

## GET /api/teacher/statistics/students

学生统计：提交次数、通过题数、总分、最近提交时间。
