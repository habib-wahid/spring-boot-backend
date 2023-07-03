package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.model.entity.RefreshToken;
import com.usb.pss.ipaservice.admin.model.entity.User;

import java.util.UUID;

public interface TokenService {

    RefreshToken createNewRefreshToken(User user);

    RefreshToken getRefreshTokenById(UUID token);

    void deleteRefreshTokenById(UUID token);
}
