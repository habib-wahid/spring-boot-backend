package com.usb.pss.ipaservice.multitenancy.context;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public final class TenantContext {

    private TenantContext() {
    }

    private static final ThreadLocal<String> CURRENT_TENANT = new InheritableThreadLocal<>();

    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    public static void setCurrentTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }

}
