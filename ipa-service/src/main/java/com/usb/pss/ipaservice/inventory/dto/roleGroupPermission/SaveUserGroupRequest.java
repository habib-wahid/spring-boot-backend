package com.usb.pss.ipaservice.inventory.dto.roleGroupPermission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserGroupRequest {
    public List<UserGroupDto> userGroupDtoList;
}
