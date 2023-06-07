package com.usb.pss.ipaservice.inventory.dto.roleGroupPermission;

import java.util.List;

public record SaveSingleUserRolesRequest(Long userId, List<RoleDto> roleList) {
}
