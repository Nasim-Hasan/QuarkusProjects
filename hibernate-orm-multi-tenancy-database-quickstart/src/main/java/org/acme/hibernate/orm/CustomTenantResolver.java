package org.acme.hibernate.orm;

import org.jboss.logging.Logger;

import io.quarkus.arc.Unremovable;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
@Unremovable
@PersistenceUnitExtension
public class CustomTenantResolver implements TenantResolver {

    private static final Logger LOG = Logger.getLogger(CustomTenantResolver.class);

    @Inject
    RoutingContext context;
    
    @Override
    public String getDefaultTenantId() {
        return "base";
    }

    @Override
    public String resolveTenantId() {
        String path = context.request().path();
        final String tenantId;
        if (path.startsWith("/mycompany")) {
            tenantId = "mycompany";
        } else {
            tenantId = getDefaultTenantId();
        }
        LOG.debugv("TenantId = {0}", tenantId);
        return tenantId;
    }

}
