# 前端架构设计

## 1. 目标目录

```text
src
├── api
├── types
├── router
├── store
├── layouts
├── components
├── views
├── utils
└── main.ts
```

## 2. 视图模块

学生端：problem-list、problem-detail、submission-history、ranking。  
教师端：problem-manage、problem-edit、submission-manage、statistics。  
管理端：user-manage、class-manage。

## 3. 核心组件

SqlEditor、ResultViewer、JudgeStatusTag、DifficultyTag、ProblemCard、SchemaViewer、SampleDataViewer、SubmissionTable。

## 4. API 模块

user.ts、problem.ts、submission.ts、judge.ts、statistics.ts、classroom.ts。

## 5. 路由

`/login`、`/student/problems`、`/student/problems/:id`、`/teacher/problems`、`/teacher/problems/:id/edit`、`/admin/users`。

## 6. 权限

前端通过路由 meta 控制菜单和跳转，后端必须做最终权限校验。
