package com.sqloj.application.judge.port;

public interface SqlSandboxManager {

    <T> T executeInSandbox(Long submissionId, SqlSandboxCallback<T> callback);
}
