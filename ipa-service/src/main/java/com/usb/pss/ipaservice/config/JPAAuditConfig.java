package com.usb.pss.ipaservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

public class JPAAuditConfig {

    @Bean
    public AuditorAware<Long> customAuditorAware() {
        return new AuditAwareImpl();
    }
}
