package com.usb.pss.ipaservice.admin.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NavigationItemResponse {

    private String label;
    private String key;
    private String url;
    private String icon;

}
