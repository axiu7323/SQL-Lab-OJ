# 表结构设计

## sys_user

字段：id、username、password、real_name、role、class_id、status、created_at、updated_at、deleted。索引：uk_username、idx_role、idx_class_id。

## oj_problem

字段：id、title、description、category_id、difficulty、score、init_schema_sql、init_data_sql、standard_sql、order_sensitive、check_column_name、time_limit_ms、max_rows、status、creator_id、created_at、updated_at、deleted。

## oj_submission

字段：id、user_id、problem_id、submit_sql、status、score、error_message、execute_time_ms、submitted_at、judged_at、created_at、updated_at、deleted。

## oj_judge_result

字段：id、submission_id、status、expected_result、actual_result、diff_type、diff_message、execute_time_ms、created_at、updated_at、deleted。

## oj_problem_category

字段：id、category_name、sort_order、status、created_at、updated_at、deleted。

## oj_classroom

字段：id、class_name、teacher_id、status、created_at、updated_at、deleted。
