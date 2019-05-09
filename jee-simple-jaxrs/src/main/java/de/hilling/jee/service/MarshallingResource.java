package de.hilling.jee.service;

import de.hilling.jee.payload.Greeting;
import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/json")
@Produces(MediaType.APPLICATION_JSON)
@Metered(absolute = true, displayName = "JSON Marshaller Demo")
@RequestScoped
public class MarshallingResource {

    @GET
    public Greeting pong() {
        return new Greeting("Hello", "Gunnar");
    }
}
