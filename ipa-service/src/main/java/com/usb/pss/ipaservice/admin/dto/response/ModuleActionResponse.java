package com.usb.pss.ipaservice.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
 * Module response for to build up the tree structure
 * with Module -> Sub-module -> Menu -> Action
 * */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleActionResponse {
    private Long id;
    private String name;
    private String description;
    private Integer sortOrder;
    private List<SubModuleActionResponse> subModules;
}
