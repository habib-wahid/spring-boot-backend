package com.usb.pss.ipaservice.inventory.dto.roleGroupPermission;

import java.util.List;

public record SaveSingleRolePermissionsRequest (Long roleId, List<PermissionDto> permissionList) {
}
