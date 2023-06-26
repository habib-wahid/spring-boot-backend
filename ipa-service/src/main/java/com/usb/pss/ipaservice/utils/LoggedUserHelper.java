package com.usb.pss.ipaservice.utils;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class LoggedUserHelper {

    private LoggedUserHelper() {}
    public static Optional<Long> getCurrentUserId() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        IpaAdminUser user = (IpaAdminUser) auth.getPrincipal();
        if(Objects.isNull(user))
            return Optional.empty();
        return Optional.of(user.getId());
    }
}
