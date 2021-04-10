package com.jinseong.soft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Audit 기능 사용을 위한 설정
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
}
