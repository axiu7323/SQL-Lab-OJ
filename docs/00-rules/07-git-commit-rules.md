# Git 提交规范

## 1. 提交原则

每完成一个 docs 任务、重构任务或功能任务，都必须提交一次 Git。小步提交，方便回滚。

## 2. 类型

- docs：文档
- feat：新功能
- fix：修复
- refactor：重构
- test：测试
- chore：配置或构建

## 3. 示例

```bash
git add .
git commit -m "docs: add sql oj architecture documents"
git commit -m "refactor: create backend ddd package structure"
git commit -m "feat: add judge domain model"
git commit -m "test: add sql safety checker tests"
```

## 4. 回滚

未提交前：`git restore .`。  
已提交后：`git revert <commit-id>`。
