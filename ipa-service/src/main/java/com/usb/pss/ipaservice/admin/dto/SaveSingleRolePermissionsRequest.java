package com.usb.pss.ipaservice.admin.dto;

import java.util.List;

public record SaveSingleRolePermissionsRequest (Long roleId, List<PermissionDto> permissionList) {
}
