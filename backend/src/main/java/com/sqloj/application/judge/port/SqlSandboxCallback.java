package com.sqloj.application.judge.port;

@FunctionalInterface
public interface SqlSandboxCallback<T> {

    T execute(SqlSandbox sandbox);
}
