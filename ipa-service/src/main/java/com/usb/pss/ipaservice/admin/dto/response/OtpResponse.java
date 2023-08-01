package com.usb.pss.ipaservice.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Junaid Khan Pathan
 * @date Jul 18, 2023
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpResponse {
    private String username;
}
