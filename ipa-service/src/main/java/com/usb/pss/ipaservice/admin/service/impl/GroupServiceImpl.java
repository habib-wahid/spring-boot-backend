package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminGroup;
import com.usb.pss.ipaservice.admin.repository.IpaAdminGroupRepository;
import com.usb.pss.ipaservice.admin.service.iservice.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final IpaAdminGroupRepository groupRepository;

    public void createNewGroup(GroupRequest request) {
        IpaAdminGroup group = IpaAdminGroup.builder()
                .name(request.name())
                .build();

        groupRepository.save(group);
    }

}
