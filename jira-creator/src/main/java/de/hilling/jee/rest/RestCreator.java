package de.hilling.jee.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/")
@ApplicationScoped
public class RestCreator {

    private static final Logger LOG = LoggerFactory.getLogger(RestCreator.class);

    @Inject
    private BackendService backendService;

    @Context
    private SecurityContext securityContext;

    @POST
    public void createIssue() {
        final Principal userPrincipal = securityContext.getUserPrincipal();
        try {
            backendService.verify(null);
            LOG.info("created issue");
        } catch (RuntimeException re) {
            LOG.error("error creating", re);
        }
    }
}
