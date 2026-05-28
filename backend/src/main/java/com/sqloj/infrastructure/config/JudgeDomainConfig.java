package com.sqloj.infrastructure.config;

import com.sqloj.domain.judge.JudgeDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JudgeDomainConfig {

    @Bean
    public JudgeDomainService judgeDomainService() {
        return new JudgeDomainService();
    }
}
