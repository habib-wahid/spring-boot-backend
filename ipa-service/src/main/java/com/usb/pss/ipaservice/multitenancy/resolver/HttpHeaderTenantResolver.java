package com.usb.pss.ipaservice.multitenancy.resolver;


import com.usb.pss.ipaservice.multitenancy.TenantHttpProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {

    private final TenantHttpProperties tenantHttpProperties;

    @Override
    public String resolveTenantId(@NonNull HttpServletRequest request) {
        return request.getHeader(tenantHttpProperties.headerName());
    }

}
