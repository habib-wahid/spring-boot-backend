package com.usba.pss.auth.dto.groupRolePermission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDto {
    private Long id;
    private Long groupId;
    private Long userId;
    private boolean active = true;
}
