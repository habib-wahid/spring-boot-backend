package com.usb.pss.ipaservice.admin.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {

    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private List<T> content;
    private List<String> header;
}
