package com.usb.pss.ipaservice.admin.dto;

import java.util.List;

public record SaveSingleGroupPermissionsRequest(Long groupId, List<PermissionDto> permissionList) {
}
