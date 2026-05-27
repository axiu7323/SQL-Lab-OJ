# 基础设施层设计

persistence/entity：UserDO、ProblemDO、SubmissionDO、JudgeResultDO。  
persistence/mapper：UserMapper、ProblemMapper、SubmissionMapper、JudgeResultMapper。  
persistence/converter：UserConverter、ProblemConverter、SubmissionConverter、JudgeResultConverter。  
repository：UserRepositoryImpl、ProblemRepositoryImpl、SubmissionRepositoryImpl。  
sandbox：MySqlSandbox、JdbcSqlExecutor。  
security：JwtTokenProvider、CurrentUserProvider、AuthenticationFilter。  
config：MyBatisPlusConfig、WebMvcConfig、CorsConfig、RedisConfig。
