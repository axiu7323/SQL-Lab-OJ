# API 接口规范

## 1. 统一响应

```json
{
  "code": 0,
  "message": "success",
  "data": {},
  "timestamp": 1710000000000
}
```

分页响应：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 100,
    "records": []
  },
  "timestamp": 1710000000000
}
```

## 2. 路径规范

公共接口：`/api/auth`、`/api/problems`、`/api/submissions`。  
教师接口：`/api/teacher/**`。  
管理接口：`/api/admin/**`。

## 3. HTTP 方法

GET 查询，POST 创建或提交，PUT 更新，DELETE 删除或逻辑删除。

## 4. 错误码

0 成功，400 参数错误，401 未登录，403 无权限，404 不存在，500 系统异常。  
业务错误：10001 用户不存在，20001 题目不存在，20002 题目未启用，30001 提交不存在，40001 SQL 不安全，40002 SQL 超时。

## 5. 修改接口的同步要求

接口字段变化必须同步更新 docs/04-api、后端 Request / Response、前端 api、前端 types 和页面调用。
