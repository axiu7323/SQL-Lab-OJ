# 前端代码规范

## 1. 技术栈

Vue 3、Vite、TypeScript、Element Plus、Monaco Editor、Pinia、Axios、Vue Router。

## 2. 目录结构

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

## 3. API 拆分

必须按业务模块拆分：user.ts、problem.ts、submission.ts、judge.ts、statistics.ts、classroom.ts。

禁止把所有接口写入一个 api.ts。

## 4. 类型定义

类型放到 src/types，按模块拆分。

## 5. 页面拆分

```text
views/login
views/student/problem-list
views/student/problem-detail
views/student/submission-history
views/student/ranking
views/teacher/problem-manage
views/teacher/problem-edit
views/teacher/submission-manage
views/teacher/statistics
views/admin/user-manage
```

## 6. 通用组件

SqlEditor、ResultViewer、JudgeStatusTag、DifficultyTag、ProblemCard、SchemaViewer、SampleDataViewer、SubmissionTable。

## 7. 路由权限

路由 meta 中配置 requiresAuth 和 roles。前端只做体验控制，后端必须做最终权限校验。

## 8. SQL 编辑器

SQL 编辑器必须封装成 SqlEditor 组件，支持 v-model、SQL 高亮、readonly、高度配置。
