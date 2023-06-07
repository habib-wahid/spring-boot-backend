package com.usb.pss.ipaservice.admin.dto;

import java.util.List;

public record SaveSingleUserGroupsRequest(Long userId, List<GroupsDto> groupList) {
}
