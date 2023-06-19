package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.repository.IpaAdminActionRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {
    private IpaAdminActionRepository adminActionRepository;
}
