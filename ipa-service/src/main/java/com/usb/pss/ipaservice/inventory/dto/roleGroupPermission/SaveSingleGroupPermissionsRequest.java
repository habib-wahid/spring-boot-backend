package com.usb.pss.ipaservice.inventory.dto.roleGroupPermission;

import java.util.List;

public record SaveSingleGroupPermissionsRequest(Long groupId, List<PermissionDto> permissionList) {
}
