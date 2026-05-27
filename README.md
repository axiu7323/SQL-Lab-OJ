# SQL OJ Docs 文档体系

本目录是 SQL OJ 判题系统的完整 docs 文档体系。建议复制到项目根目录，然后让 Codex 严格按照 `docs/09-tasks` 中的任务文件逐步执行。

## 推荐使用方式

1. 先读 `PROJECT_SPEC.md`。
2. 再读 `docs/00-rules`，这是长期约束。
3. 再读 `docs/01-architecture`，这是目标架构。
4. 再读 `docs/03-modules`，这是各模块需求。
5. 再读 `docs/04-api` 和 `docs/05-database`，这是接口和数据契约。
6. 最后执行 `docs/09-tasks` 中的任务文件。
7. 每个任务完成后用 `docs/10-review` 清单验收。
8. 验收通过后 Git 提交。

## 推荐 Codex 提示词

```text
请严格按照 docs/09-tasks/001-review-current-project.md 执行任务。
执行前必须阅读任务文件中列出的所有规范文件。
本轮只允许执行当前任务，不允许修改任务范围之外的文件。
修改前先输出执行计划，修改后输出变更文件清单、验收结果和未完成事项。
```
