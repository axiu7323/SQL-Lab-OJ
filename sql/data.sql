-- SQL Lab OJ seed data for local demo and later Docker initialization.

USE sql_lab_oj;

SET NAMES utf8mb4;

INSERT INTO sys_role (id, role_code, role_name, status)
VALUES
    (1, 'TEACHER', 'Teacher', 'ACTIVE'),
    (2, 'STUDENT', 'Student', 'ACTIVE'),
    (3, 'ADMIN', 'Admin', 'ACTIVE')
ON DUPLICATE KEY UPDATE
    role_name = VALUES(role_name),
    status = VALUES(status);

INSERT INTO oj_classroom (id, class_name, teacher_id, status)
VALUES
    (1, 'SQL Basic Class 01', 1001, 'ACTIVE')
ON DUPLICATE KEY UPDATE
    class_name = VALUES(class_name),
    teacher_id = VALUES(teacher_id),
    status = VALUES(status);

INSERT INTO sys_user (id, username, password_hash, real_name, role, class_id, status)
VALUES
    (1001, 'teacher_demo', 'demo-password-hash', 'Teacher Demo', 'TEACHER', NULL, 'ACTIVE'),
    (2001, 'student_demo', 'demo-password-hash', 'Student Demo', 'STUDENT', 1, 'ACTIVE')
ON DUPLICATE KEY UPDATE
    password_hash = VALUES(password_hash),
    real_name = VALUES(real_name),
    role = VALUES(role),
    class_id = VALUES(class_id),
    status = VALUES(status);

INSERT INTO oj_class_student (id, class_id, student_id, status)
VALUES
    (1, 1, 2001, 'ACTIVE')
ON DUPLICATE KEY UPDATE
    status = VALUES(status);

INSERT INTO oj_problem_category (id, category_name, sort_order, status)
VALUES
    (1, 'Basic SELECT', 1, 'ACTIVE')
ON DUPLICATE KEY UPDATE
    category_name = VALUES(category_name),
    sort_order = VALUES(sort_order),
    status = VALUES(status);

INSERT INTO oj_problem (
    id,
    title,
    description,
    category_id,
    category_name,
    difficulty,
    score,
    init_schema_sql,
    init_data_sql,
    standard_sql,
    order_sensitive,
    check_column_name,
    time_limit_ms,
    max_rows,
    memory_limit_mb,
    status,
    creator_id
)
VALUES (
    1,
    'Query all student names',
    'Write a SELECT statement to query all student names from the student table. Return one column named name.',
    1,
    'Basic SELECT',
    'EASY',
    100,
    'CREATE TABLE student (id BIGINT PRIMARY KEY, name VARCHAR(64) NOT NULL, age INT NOT NULL);',
    'INSERT INTO student (id, name, age) VALUES (1, ''Alice'', 18), (2, ''Bob'', 19), (3, ''Cindy'', 20);',
    'SELECT name FROM student;',
    0,
    1,
    3000,
    1000,
    256,
    'ENABLED',
    1001
)
ON DUPLICATE KEY UPDATE
    title = VALUES(title),
    description = VALUES(description),
    category_id = VALUES(category_id),
    category_name = VALUES(category_name),
    difficulty = VALUES(difficulty),
    score = VALUES(score),
    init_schema_sql = VALUES(init_schema_sql),
    init_data_sql = VALUES(init_data_sql),
    standard_sql = VALUES(standard_sql),
    order_sensitive = VALUES(order_sensitive),
    check_column_name = VALUES(check_column_name),
    time_limit_ms = VALUES(time_limit_ms),
    max_rows = VALUES(max_rows),
    memory_limit_mb = VALUES(memory_limit_mb),
    status = VALUES(status),
    creator_id = VALUES(creator_id);

INSERT INTO oj_submission (
    id,
    user_id,
    problem_id,
    submit_sql,
    status,
    score,
    error_message,
    execute_time_ms,
    submitted_at,
    judged_at
)
VALUES (
    1,
    2001,
    1,
    'SELECT name FROM student;',
    'PENDING',
    0,
    '',
    0,
    CURRENT_TIMESTAMP,
    NULL
)
ON DUPLICATE KEY UPDATE
    user_id = VALUES(user_id),
    problem_id = VALUES(problem_id),
    submit_sql = VALUES(submit_sql),
    status = VALUES(status),
    score = VALUES(score),
    error_message = VALUES(error_message),
    execute_time_ms = VALUES(execute_time_ms),
    submitted_at = VALUES(submitted_at),
    judged_at = VALUES(judged_at);

INSERT INTO oj_student_statistics (id, student_id, submit_count, passed_count, total_score, last_submitted_at)
VALUES
    (1, 2001, 1, 0, 0, CURRENT_TIMESTAMP)
ON DUPLICATE KEY UPDATE
    submit_count = VALUES(submit_count),
    passed_count = VALUES(passed_count),
    total_score = VALUES(total_score),
    last_submitted_at = VALUES(last_submitted_at);

INSERT INTO oj_problem_statistics (id, problem_id, submit_count, accepted_count, accepted_student_count, pass_rate)
VALUES
    (1, 1, 1, 0, 0, 0)
ON DUPLICATE KEY UPDATE
    submit_count = VALUES(submit_count),
    accepted_count = VALUES(accepted_count),
    accepted_student_count = VALUES(accepted_student_count),
    pass_rate = VALUES(pass_rate);
