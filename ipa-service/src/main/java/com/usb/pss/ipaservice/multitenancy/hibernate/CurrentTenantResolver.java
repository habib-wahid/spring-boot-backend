package com.usb.pss.ipaservice.multitenancy.hibernate;


import com.usb.pss.ipaservice.multitenancy.context.TenantContext;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CurrentTenantResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {

    public static final String DEFAULT_SCHEMA = "master";

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantContext.getCurrentTenant() != null
                ? TenantContext.getCurrentTenant()
                : DEFAULT_SCHEMA;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }

}
