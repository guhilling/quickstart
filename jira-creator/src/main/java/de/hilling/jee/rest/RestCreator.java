package de.hilling.jee.rest;

import de.hilling.jee.jira.JiraServiceAdapter;
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
    private JiraServiceAdapter serviceAdapter;

    @Context
    private SecurityContext securityContext;

    @POST
    public void createIssue() {
        final Principal userPrincipal = securityContext.getUserPrincipal();
        LOG.info("created issue");
    }
}
