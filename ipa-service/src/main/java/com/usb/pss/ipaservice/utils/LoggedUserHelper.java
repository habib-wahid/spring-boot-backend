package com.usb.pss.ipaservice.utils;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class LoggedUserHelper {

    public static Long getCurrentUserId(){
        Long userId = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(auth))
            return null;
        IpaAdminUser ipaAdminUser = (IpaAdminUser) auth.getPrincipal();
        if(Objects.nonNull(ipaAdminUser)){
            userId = ipaAdminUser.getId();
        }
        return userId;
    }
}
