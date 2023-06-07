package com.usb.pss.ipaservice.admin.dto;

import java.util.List;

public record SaveSingleUserRolesRequest(Long userId, List<RoleDto> roleList) {
}
