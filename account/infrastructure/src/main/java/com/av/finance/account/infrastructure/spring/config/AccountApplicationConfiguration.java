package com.av.finance.account.infrastructure.spring.config;

import com.av.finance.account.common.RequestProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@EnableRetry(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "com.av.finance.account")
@PropertySource("classpath:request.properties")
@Configuration
public class AccountApplicationConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "request")
    public RequestProperties requestProperties() {
        return new RequestProperties();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) {
        return templateBuilder.rootUri(requestProperties().getBaseUrl())
                .setConnectTimeout(Duration.ofMillis(requestProperties().getTimeout()))
                .setReadTimeout(Duration.ofMillis(requestProperties().getReadTimeout()))
                .build();
    }
}
