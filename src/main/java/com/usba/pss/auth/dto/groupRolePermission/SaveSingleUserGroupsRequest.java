package com.usba.pss.auth.dto.groupRolePermission;

import java.util.List;

public record SaveSingleUserGroupsRequest(Long userId, List<GroupsDto> groupList) {
}
