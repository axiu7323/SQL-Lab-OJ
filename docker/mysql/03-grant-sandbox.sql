-- Local Docker demo privilege for SQL judge sandbox databases.
-- The judge engine creates and drops temporary databases named judge_*.
-- Production deployments should replace this with a dedicated sandbox user policy.
GRANT CREATE, DROP ON *.* TO 'sqloj'@'%';
FLUSH PRIVILEGES;
