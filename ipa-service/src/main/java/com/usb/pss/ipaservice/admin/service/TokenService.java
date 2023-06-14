package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminRefreshToken;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;

import java.util.UUID;

public interface TokenService {

    IpaAdminRefreshToken createNewRefreshToken(IpaAdminUser user);

    IpaAdminRefreshToken getRefreshTokenById(UUID token);

    void deleteRefreshTokenById(UUID token);
}
