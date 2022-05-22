package com.av.finance.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@EnableRetry(proxyTargetClass = true)
@EnableJpaRepositories
@SpringBootApplication
public class AccountServiceApplication {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) {
        return templateBuilder.rootUri("http://localhost:8092")
                .setConnectTimeout(Duration.ofMillis(500L))
                .setReadTimeout(Duration.ofMillis(1000L))
                .build();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
                .withinMillis(4000L)
                .fixedBackoff(1000L)
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}
