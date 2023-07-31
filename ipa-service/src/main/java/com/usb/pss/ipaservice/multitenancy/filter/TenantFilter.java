package com.usb.pss.ipaservice.multitenancy.filter;


import com.usb.pss.ipaservice.admin.service.JwtService;
import com.usb.pss.ipaservice.multitenancy.context.TenantContext;
import com.usb.pss.ipaservice.multitenancy.resolver.HttpHeaderTenantResolver;
import io.micrometer.common.KeyValue;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.ServerHttpObservationFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TenantFilter extends OncePerRequestFilter {

    private final HttpHeaderTenantResolver httpRequestTenantResolver;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String tenantId = jwtService.getTenant(request);
        if (!Objects.isNull(tenantId)) {
            TenantContext.setCurrentTenant(tenantId);
        } else {
            tenantId = httpRequestTenantResolver.resolveTenantId(request);
            Optional.ofNullable(tenantId).ifPresent(tenant -> {
                TenantContext.setCurrentTenant(tenant);
                configureLogs(tenant);
                configureTraces(tenant, request);
            });
        }

        filterChain.doFilter(request, response);

        clear();
    }

    private void configureLogs(String tenantId) {
        MDC.put("tenantId", tenantId);
    }

    private void configureTraces(String tenantId, HttpServletRequest request) {
        ServerHttpObservationFilter.findObservationContext(request).ifPresent(context ->
            context.addHighCardinalityKeyValue(KeyValue.of("tenant.id", tenantId)));
    }

    private void clear() {
        MDC.remove("tenantId");
        TenantContext.clear();
    }

}
