package com.usb.pss.ipaservice.admin.dto.response;

import lombok.*;

import java.util.List;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuActionResponse {
    private Long id;
    private String name;
    private List<ActionResponse> actions;
}
