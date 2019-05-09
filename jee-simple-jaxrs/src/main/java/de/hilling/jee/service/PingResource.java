package de.hilling.jee.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pong")
@Produces(MediaType.TEXT_PLAIN)
@ApplicationScoped
@Transactional
public class PingResource {

    @ConfigProperty(name = "pong.message", defaultValue = "hello")
    @Inject
    protected String message;

    @GET
    public String pong() {
        return message;
    }
}
