package de.hilling.jee.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pong")
@Produces(MediaType.TEXT_PLAIN)
public class PingResource {

    @GET
    public String pong() {
        return "hello";
    }
}
