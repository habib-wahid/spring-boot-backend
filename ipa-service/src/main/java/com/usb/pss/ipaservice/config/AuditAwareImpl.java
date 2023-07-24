package com.usb.pss.ipaservice.config;

import com.usb.pss.ipaservice.utils.LoggedUserHelper;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        return LoggedUserHelper.getCurrentUserId();
    }
}
