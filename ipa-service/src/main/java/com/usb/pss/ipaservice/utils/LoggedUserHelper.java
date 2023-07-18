package com.usb.pss.ipaservice.utils;

import com.usb.pss.ipaservice.admin.model.entity.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class LoggedUserHelper {

    private LoggedUserHelper() {
    }

    public static Optional<Long> getCurrentUserId() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.ofNullable(auth.getPrincipal())
            .filter(User.class::isInstance)
            .map(User.class::cast)
            .map(User::getId);

    }


    public static Optional<User> getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.ofNullable(auth.getPrincipal())
            .filter(User.class::isInstance)
            .map(User.class::cast);

    }

}
