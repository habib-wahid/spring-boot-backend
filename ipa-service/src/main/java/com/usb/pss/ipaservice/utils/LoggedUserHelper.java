package com.usb.pss.ipaservice.utils;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class LoggedUserHelper {

    public static Optional<Long> getCurrentUserId() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth))
            return Optional.empty();
        IpaAdminUser ipaAdminUser = (IpaAdminUser) auth.getPrincipal();
        if (Objects.isNull(ipaAdminUser)) {
            return Optional.empty();
        }
        return Optional.of(ipaAdminUser.getId());
    }
}
