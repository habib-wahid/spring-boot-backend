package com.usba.pss.auth.dto.groupRolePermission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserRoleRequest {
    public List<UserRoleDto> userRoleDtoList;
}
