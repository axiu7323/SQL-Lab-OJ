# API 客户端设计

Axios 封装在 src/utils/request.ts。请求拦截器自动携带 Token，响应拦截器统一处理 401、403、500 和业务错误。

API 文件按模块拆分：user.ts、problem.ts、submission.ts、judge.ts、statistics.ts、classroom.ts。

类型定义放在 src/types。
