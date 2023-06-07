package com.usb.pss.ipaservice.inventory.dto.roleGroupPermission;

import java.util.List;

public record SaveSingleUserGroupsRequest(Long userId, List<GroupsDto> groupList) {
}
