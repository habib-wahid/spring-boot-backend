package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminGroup;
import com.usb.pss.ipaservice.admin.repository.IpaAdminGroupRepository;
import com.usb.pss.ipaservice.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final IpaAdminGroupRepository groupRepository;

    public GenericResponse createNewGroup(GroupRequest request) {
        IpaAdminGroup group = IpaAdminGroup.builder()
                .name(request.name())
                .build();

        groupRepository.save(group);
        return new GenericResponse();

    }

}
