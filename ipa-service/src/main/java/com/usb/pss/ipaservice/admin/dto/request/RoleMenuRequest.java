package com.usb.pss.ipaservice.admin.dto.request;

import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public record RoleMenuRequest(
    List<Long> roleMenuIds
) {
}
