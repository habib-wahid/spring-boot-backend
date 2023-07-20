package com.usb.pss.ipaservice.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private Long id;
    private String name;
    private String description;
    private String screenId;
    private String url;
    private String icon;
    private Integer sortOrder;
    private List<ActionResponse> actions;
}
