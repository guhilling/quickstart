package de.hilling.jee.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@ApplicationScoped
public class BackendService {

    private static final Logger LOG = LoggerFactory.getLogger(BackendService.class);

    public void verify(@NotNull Principal userPrincipal) {
        LOG.info("called by {}", userPrincipal);
    }
}
