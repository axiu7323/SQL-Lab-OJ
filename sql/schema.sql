-- SQL Lab OJ schema for MySQL 8.
-- This file creates the application database and V1 tables used by the current backend.

CREATE DATABASE IF NOT EXISTS sql_lab_oj
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE sql_lab_oj;

SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT NOT NULL COMMENT 'role id',
    role_code VARCHAR(32) NOT NULL COMMENT 'role code: STUDENT, TEACHER, ADMIN',
    role_name VARCHAR(64) NOT NULL COMMENT 'role display name',
    status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE' COMMENT 'role status',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'logic delete flag',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_code (role_code),
    KEY idx_role_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='system role table';

CREATE TABLE IF NOT EXISTS oj_classroom (
    id BIGINT NOT NULL COMMENT 'class id',
    class_name VARCHAR(128) NOT NULL COMMENT 'class name',
    teacher_id BIGINT NULL COMMENT 'teacher user id',
    status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE' COMMENT 'class status',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'logic delete flag',
    PRIMARY KEY (id),
    KEY idx_teacher_id (teacher_id),
    KEY idx_class_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='class table';

CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT NOT NULL COMMENT 'user id',
    username VARCHAR(64) NOT NULL COMMENT 'login username',
    password_hash VARCHAR(255) NOT NULL COMMENT 'password hash',
    real_name VARCHAR(64) NOT NULL COMMENT 'real name',
    role VARCHAR(32) NOT NULL COMMENT 'user role: STUDENT, TEACHER, ADMIN',
    class_id BIGINT NULL COMMENT 'class id for student',
    status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE' COMMENT 'user status',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'logic delete flag',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_role (role),
    KEY idx_class_id (class_id),
    KEY idx_user_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='system user table';

CREATE TABLE IF NOT EXISTS oj_class_student (
    id BIGINT NOT NULL COMMENT 'relation id',
    class_id BIGINT NOT NULL COMMENT 'class id',
    student_id BIGINT NOT NULL COMMENT 'student user id',
    joined_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'joined time',
    status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE' COMMENT 'relation status',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'logic delete flag',
    PRIMARY KEY (id),
    UNIQUE KEY uk_class_student (class_id, student_id),
    KEY idx_class_id (class_id),
    KEY idx_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='class student relation table';

CREATE TABLE IF NOT EXISTS oj_problem_category (
    id BIGINT NOT NULL COMMENT 'category id',
    category_name VARCHAR(128) NOT NULL COMMENT 'category name',
    sort_order INT NOT NULL DEFAULT 0 COMMENT 'sort order',
    status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE' COMMENT 'category status',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'logic delete flag',
    PRIMARY KEY (id),
    UNIQUE KEY uk_category_name (category_name),
    KEY idx_category_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='problem category table';

CREATE TABLE IF NOT EXISTS oj_problem (
    id BIGINT NOT NULL COMMENT 'problem id',
    title VARCHAR(255) NOT NULL COMMENT 'problem title',
    description TEXT NOT NULL COMMENT 'problem description',
    category_id BIGINT NOT NULL COMMENT 'category id',
    category_name VARCHAR(128) NOT NULL COMMENT 'category name snapshot',
    difficulty VARCHAR(32) NOT NULL COMMENT 'difficulty: EASY, MEDIUM, HARD',
    score INT NOT NULL DEFAULT 100 COMMENT 'problem score',
    init_schema_sql MEDIUMTEXT NOT NULL COMMENT 'schema sql executed before judging',
    init_data_sql MEDIUMTEXT NOT NULL COMMENT 'seed data sql executed before judging',
    standard_sql MEDIUMTEXT NOT NULL COMMENT 'expected answer sql',
    order_sensitive TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'whether row order matters',
    check_column_name TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'whether column names must match',
    time_limit_ms INT NOT NULL DEFAULT 3000 COMMENT 'query timeout in milliseconds',
    max_rows INT NOT NULL DEFAULT 1000 COMMENT 'max rows allowed in result set',
    memory_limit_mb INT NOT NULL DEFAULT 256 COMMENT 'reserved memory limit',
    status VARCHAR(32) NOT NULL DEFAULT 'DRAFT' COMMENT 'problem status: DRAFT, ENABLED, DISABLED',
    creator_id BIGINT NULL COMMENT 'creator user id',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'logic delete flag',
    PRIMARY KEY (id),
    KEY idx_category_id (category_id),
    KEY idx_difficulty (difficulty),
    KEY idx_status (status),
    KEY idx_creator_id (creator_id),
    KEY idx_status_category_difficulty (status, category_id, difficulty)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='SQL problem table';

CREATE TABLE IF NOT EXISTS oj_submission (
    id BIGINT NOT NULL COMMENT 'submission id',
    user_id BIGINT NOT NULL COMMENT 'student user id',
    problem_id BIGINT NOT NULL COMMENT 'problem id',
    submit_sql MEDIUMTEXT NOT NULL COMMENT 'student submitted sql',
    status VARCHAR(32) NOT NULL DEFAULT 'PENDING' COMMENT 'submission status',
    score INT NOT NULL DEFAULT 0 COMMENT 'score',
    error_message TEXT NULL COMMENT 'error message',
    execute_time_ms BIGINT NOT NULL DEFAULT 0 COMMENT 'execution time in milliseconds',
    submitted_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'submitted time',
    judged_at DATETIME NULL COMMENT 'judged time',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'logic delete flag',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_problem_id (problem_id),
    KEY idx_status (status),
    KEY idx_submitted_at (submitted_at),
    KEY idx_user_problem (user_id, problem_id),
    KEY idx_user_time (user_id, submitted_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='SQL submission table';

CREATE TABLE IF NOT EXISTS oj_judge_result (
    id BIGINT NOT NULL COMMENT 'judge result id, currently equals submission id',
    submission_id BIGINT NOT NULL COMMENT 'submission id',
    problem_id BIGINT NULL COMMENT 'problem id snapshot',
    user_id BIGINT NULL COMMENT 'student user id snapshot',
    status VARCHAR(32) NOT NULL COMMENT 'judge status',
    score INT NOT NULL DEFAULT 0 COMMENT 'judge score',
    error_code VARCHAR(32) NULL COMMENT 'judge error code',
    error_message TEXT NULL COMMENT 'judge error message',
    error_detail TEXT NULL COMMENT 'judge error detail',
    expected_result JSON NULL COMMENT 'reserved expected result snapshot',
    actual_result JSON NULL COMMENT 'reserved actual result snapshot',
    diff_type VARCHAR(64) NULL COMMENT 'reserved diff type',
    diff_message TEXT NULL COMMENT 'reserved diff message',
    execute_time_ms BIGINT NOT NULL DEFAULT 0 COMMENT 'execution time in milliseconds',
    judged_at DATETIME NULL COMMENT 'judged time',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'logic delete flag',
    PRIMARY KEY (id),
    UNIQUE KEY uk_submission_id (submission_id),
    KEY idx_problem_id (problem_id),
    KEY idx_user_id (user_id),
    KEY idx_status (status),
    KEY idx_judged_at (judged_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='judge result table';

CREATE TABLE IF NOT EXISTS oj_problem_statistics (
    id BIGINT NOT NULL COMMENT 'statistics id',
    problem_id BIGINT NOT NULL COMMENT 'problem id',
    submit_count BIGINT NOT NULL DEFAULT 0 COMMENT 'total submit count',
    accepted_count BIGINT NOT NULL DEFAULT 0 COMMENT 'accepted submit count',
    accepted_student_count BIGINT NOT NULL DEFAULT 0 COMMENT 'accepted student count',
    pass_rate DECIMAL(8,4) NOT NULL DEFAULT 0 COMMENT 'pass rate',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'logic delete flag',
    PRIMARY KEY (id),
    UNIQUE KEY uk_problem_id (problem_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='problem statistics table';

CREATE TABLE IF NOT EXISTS oj_student_statistics (
    id BIGINT NOT NULL COMMENT 'statistics id',
    student_id BIGINT NOT NULL COMMENT 'student user id',
    submit_count BIGINT NOT NULL DEFAULT 0 COMMENT 'submit count',
    passed_count BIGINT NOT NULL DEFAULT 0 COMMENT 'passed problem count',
    total_score INT NOT NULL DEFAULT 0 COMMENT 'total score',
    last_submitted_at DATETIME NULL COMMENT 'last submitted time',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT 'logic delete flag',
    PRIMARY KEY (id),
    UNIQUE KEY uk_student_id (student_id),
    KEY idx_total_score (total_score)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='student statistics table';
