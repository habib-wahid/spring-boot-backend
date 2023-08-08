package com.usb.pss.ipaservice.admin.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {

    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private List<T> content;
    private Map<String, String> header;
}
