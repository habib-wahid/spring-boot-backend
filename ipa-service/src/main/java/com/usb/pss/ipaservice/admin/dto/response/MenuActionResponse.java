package com.usb.pss.ipaservice.admin.dto.response;

import lombok.*;

import java.util.List;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuActionResponse {
    private Long menuId;
    private String menuName;
    private List<ActionResponse> actions;
}
