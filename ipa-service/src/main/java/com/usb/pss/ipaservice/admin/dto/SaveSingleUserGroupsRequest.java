package com.usb.pss.ipaservice.admin.dto;

import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;

import java.util.List;

public record SaveSingleUserGroupsRequest(Long userId, List<GroupRequest> groupList) {
}
