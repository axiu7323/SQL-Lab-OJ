# 索引设计

## sys_user

uk_username 用于登录，idx_role 用于角色筛选，idx_class_id 用于班级学生查询。

## oj_problem

idx_category_id、idx_difficulty、idx_status、idx_creator_id。常见组合可以后续加 idx_status_category_difficulty。

## oj_submission

idx_user_id、idx_problem_id、idx_status、idx_submitted_at、idx_user_problem。我的提交可加 idx_user_time(user_id, submitted_at)。

## oj_judge_result

uk_submission_id，保证一个提交对应一个判题明细。
