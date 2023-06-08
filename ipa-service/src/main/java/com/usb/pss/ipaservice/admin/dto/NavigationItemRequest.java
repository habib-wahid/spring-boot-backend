package com.usb.pss.ipaservice.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NavigationItemRequest {

    private String label;
    private String key;
    private String url;
    private String icon;
    private Long parentId;

}
